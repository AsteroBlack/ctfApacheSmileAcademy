package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.repository.ActionUtilisateurRepository;
import ci.smile.simswaporange.dao.repository.StatusRepository;
import ci.smile.simswaporange.dao.repository.TypeNumeroRepository;
import ci.smile.simswaporange.proxy.customizeclass.functions.ObjectUtilities;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.ParamsUtils;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.dto.customize.Result;
import ci.smile.simswaporange.utils.enums.StatusApiEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class InitialiserConfigBatch{
    private final ParamsUtils paramsUtils;
    private final EntityManager entityManager;
    private final ObjectUtilities objectUtilities;
    private final StatusRepository statusRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TypeNumeroRepository typeNumeroRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final SimSwapServiceProxyService simSwapServiceProxyService;
    private final ActionUtilisateurRepository actionUtilisateurRepository;
    private final RedisNumbersBatchItemReader redisNumbersBatchItemReader;
    private final RedisTemplate<String, Result> stringResultRedisTemplate;
    private final RedisTemplate <String, Long> redisTemplateLengthOfReaderUnLockNumbers;
    private final RedisRefreshNumbersBatchItemReader redisRefreshNumbersBatchItemReader;
    private final RedisTemplate <String, LockUnLockFreezeDtos> redisSimSwapServiceProxyServiceTemplate;
    private final RedisNumberBatchItemReaderActionUtilisateur redisNumberBatchItemReaderActionUtilisateur;
    private final RedisTemplate <String, LockUnLockFreezeDto> redisSimSwapServiceProxyServiceTemplateRefreshNumber;


    @Bean
    public FlatFileItemReader <ActionUtilisateur> linesReader(){
        FlatFileItemReader <ActionUtilisateur> itemReader = new FlatFileItemReader <>();
        String filePath = Utilities.getFileFromTomcat(paramsUtils);
        log.info("fichier {}",filePath);
        itemReader.setLinesToSkip(1);
//        itemReader.setResource(new FileSystemResource("/home/lenovo/Documents/dumpsecusimswap20240325.csv"));
        itemReader.setName("csvReader");
        itemReader.setResource(new FileSystemResource(filePath));
        DefaultLineMapper <ActionUtilisateur> customerLineMapper = new DefaultLineMapper <>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("NUMERO","NUMERO_MACHINE","STATUT_BLOCAGE","CO_ID","PORT_NUM","SM_SERIALNUM","TMCODE","OFFRE","DATE_ACTIVATION","STATUT_TELCO");
        tokenizer.setStrict(false);
        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new InitializerBatchMapping(objectUtilities,statusRepository,typeNumeroRepository));
        customerLineMapper.afterPropertiesSet();
        itemReader.setLineMapper(customerLineMapper);
        return itemReader;
    }

    @Bean
    public InitialiserCorporateNumbersBatch processor(){
        return new InitialiserCorporateNumbersBatch(statusRepository,simSwapServiceProxyService,actionUtilisateurRepository,stringResultRedisTemplate);
    }


    @Bean
    public ItemWriter <ActionUtilisateur> linesWriter(){
        return actionUtilisateur->actionUtilisateurRepository.saveAll(actionUtilisateur);

    }

    @Bean
    public Step readDetectAndInsertOnCorailStep1(){
        return stepBuilderFactory.get("readDetectAndInsertOnCorailStep1")
                . <ActionUtilisateur, ActionUtilisateur>chunk(1000)
                .reader(linesReader())
                .processor(processor())
                .writer(linesWriter())
                .build();
    }

    @Bean
    public InitialiserCorporateResultsNumbersBatch resultProcessor(){
        return new InitialiserCorporateResultsNumbersBatch();
    }

    public Step readDetectAndInsertOnCorailStep2(){
        return stepBuilderFactory.get("readDetectAndInsertOnCorailStep2")
                . <ActionUtilisateur, ActionUtilisateur>chunk(1000)
                .reader(redisNumberBatchItemReaderActionUtilisateur)
                .processor(resultProcessor())
                .writer(new NullItemWriter <>())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(readDetectAndInsertOnCorailStep1())
                .next(readDetectAndInsertOnCorailStep2())
                .build();
    }

    // second Job
    @Bean
    public JpaPagingItemReader <ActionUtilisateur> dbReaderForLockUnlockNumbers(){
        log.info("READER FIRST  STEP");
        Status statusDebloque = statusRepository.findByCode(StatusApiEnum.DEBLOCK,false);
        // Compte le nombre d'éléments correspondant à vos critères
        Date currentDate = new Date();
        Long dureeBlocage = paramsUtils.getDureeBlocage();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date blockStartDate = new Date(currentDate.getTime()-dureeBlocage * 3600 * 1000); // Convertir les heures en millisecondes
        Long totalItems = entityManager.createQuery("select count(e) from ActionUtilisateur e where e.status.id = :idStatus and e.isDeleted = :isDeleted and e.updatedAt <= :blockStartDate",Long.class)
                .setParameter("idStatus",statusDebloque.getId())
                .setParameter("isDeleted",false)
                .setParameter("blockStartDate",blockStartDate)
                .getSingleResult();
        log.info("Total items in database for unlock numbers: {}",totalItems);
        log.info("date et heure de la date d'aujourd'hui: {}",sqlFormat.format(currentDate));
        log.info("date et heure de la date d'aujourd'hui - 24 heures: {}",sqlFormat.format(blockStartDate));
        redisTemplateLengthOfReaderUnLockNumbers.opsForValue().set("totalItemsUnlockNumbers",totalItems);
        JpaPagingItemReader <ActionUtilisateur> reader = new JpaPagingItemReader <>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select e from ActionUtilisateur e where e.status.id = :idStatus and e.isDeleted = :isDeleted "+
                "and e.updatedAt <= :blockStartDate");
        Map <String, Object> parameters = new HashMap <>();
        parameters.put("idStatus",statusDebloque.getId());
        parameters.put("isDeleted",false);
        parameters.put("blockStartDate",blockStartDate);
        reader.setParameterValues(parameters);
        reader.setPageSize(500);
        return reader;
    }


    @Bean
    public CollectUnlockNumbersProcessor lockUnlockNumbersProcessor(){
        return new CollectUnlockNumbersProcessor(paramsUtils,redisTemplateLengthOfReaderUnLockNumbers,simSwapServiceProxyService,redisSimSwapServiceProxyServiceTemplate);
    }

    @Bean
    public ItemWriter <ActionUtilisateur> lockUnlockNumbersWriterFirstStep(){
        return actionUtilisation->actionUtilisateurRepository.saveAll(actionUtilisation);
    }

    @Bean
    public Step stepLockUnlockNumberFirstStep(){
        return stepBuilderFactory.get("stepLockUnlockNumber")
                . <ActionUtilisateur, ActionUtilisateur>chunk(1000)
                .reader(dbReaderForLockUnlockNumbers())
                .processor(lockUnlockNumbersProcessor())
                .writer(lockUnlockNumbersWriterFirstStep())
                .build();
    }

    @Bean
    public RedisNumberBatchItemProcessor lockUnlockNumbersProcessorSecondStep(){
        return new RedisNumberBatchItemProcessor(statusRepository,actionUtilisateurRepository,redisSimSwapServiceProxyServiceTemplateRefreshNumber);
    }

    @Bean
    public Step stepLockUnlockNumberSecondStep(){
        return stepBuilderFactory.get("stepLockUnlockNumberSecondStep")
                . <LockUnLockFreezeDtos, LockUnLockFreezeDtos>chunk(1000)
                .reader(redisNumbersBatchItemReader)
                .processor(lockUnlockNumbersProcessorSecondStep())
                .writer(new NullItemWriter <>())
                .build();
    }

    @Bean
    public Job jobLockUnlockNumber(){
        return jobBuilderFactory.get("jobLockUnlockNumber")
                .start(stepLockUnlockNumberFirstStep())
                .next(stepLockUnlockNumberSecondStep())
                .build();
    }


    @Bean
    public JpaPagingItemReader <ActionUtilisateur> dbReaderForRefreshNumbers(){
        log.info("READER FIRST STEP ReaderForRefreshNumbers");
        // Compte le nombre d'éléments correspondant à vos critères
        Long totalItems = entityManager.createQuery("select count(e) from ActionUtilisateur e where  e.isDeleted = :isDeleted",Long.class)
                .setParameter("isDeleted",false)
                .getSingleResult();
        log.info("Total items in database for refresh number : {}",totalItems);
        redisTemplateLengthOfReaderUnLockNumbers.opsForValue().set("totalItemsRefreshNumbers",totalItems);
        JpaPagingItemReader <ActionUtilisateur> reader = new JpaPagingItemReader <>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select e from ActionUtilisateur e where e.isDeleted = :isDeleted");
        Map <String, Object> parameters = new HashMap <>();
        parameters.put("isDeleted",false);
        reader.setParameterValues(parameters);
        reader.setPageSize(500);
        return reader;
    }

    @Bean
    public CollectRefreshNumbersProcessor refreshNumbersProcessor(){
        return new CollectRefreshNumbersProcessor(redisTemplateLengthOfReaderUnLockNumbers,simSwapServiceProxyService,redisSimSwapServiceProxyServiceTemplateRefreshNumber);
    }

    @Bean
    public ItemWriter <ActionUtilisateur> refreshNumbersWriterFirstStep(){
        return actionUtilisateur->actionUtilisateurRepository.saveAll(actionUtilisateur);
    }

    @Bean
    public RedisRefrehNumberItemProcessor refreshNumbersProcessorSecondStep(){
        return new RedisRefrehNumberItemProcessor(statusRepository,actionUtilisateurRepository, redisSimSwapServiceProxyServiceTemplateRefreshNumber);
    }

    @Bean
    public Step stepRefreshFirstStep(){
        return stepBuilderFactory.get("stepRefreshFirstStep")
                . <ActionUtilisateur, ActionUtilisateur>chunk(1000)
                .reader(dbReaderForRefreshNumbers())
                .processor(refreshNumbersProcessor())
                .writer(refreshNumbersWriterFirstStep())
                .build();
    }

    @Bean
    public Step stepRefreshNumberSecondStep(){
        return stepBuilderFactory.get("stepRefreshNumberSecondStep")
                . <LockUnLockFreezeDto, LockUnLockFreezeDto>chunk(1000)
                .reader(redisRefreshNumbersBatchItemReader)
                .processor(refreshNumbersProcessorSecondStep())
                .writer(new NullItemWriter <>())
                .build();
    }

    @Bean
    public Job jobRefreshNumber(){
        return jobBuilderFactory.get("jobRefreshNumber")
                .start(stepRefreshFirstStep())
                .next(stepRefreshNumberSecondStep())
                .build();
    }

    //thread

}

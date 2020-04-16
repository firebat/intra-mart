package jp.co.intra_mart.foundation.logic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingDefinition;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingRule;
import jp.co.intra_mart.foundation.logic.element.metadata.ApplicationElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.ElementKey;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowConstants;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import jp.co.intra_mart.foundation.logic.flow.sample.HelloTask;
import jp.co.intra_mart.foundation.logic.flow.sample.PrintTask;
import jp.co.intra_mart.system.logic.data.beans.JavaBeansDataDefinitionBuilder;
import jp.co.intra_mart.system.logic.data.mapping.StandardMappingDefinition;
import jp.co.intra_mart.system.logic.data.mapping.StandardMappingRule;
import jp.co.intra_mart.system.logic.data.mapping.StandardPath;
import jp.co.intra_mart.system.logic.data.mapping.StandardValue;
import jp.co.intra_mart.system.logic.impl.IntramartLogicServiceConfiguration;
import jp.co.intra_mart.system.logic.repository.StandardApplicationElementMetadataRepository;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicServiceProviderTest {

    @Test
    public void testAll() throws Exception {
        // 逻辑服务配置
        LogicServiceConfiguration configuration = new IntramartLogicServiceConfiguration();

        // 注册节点逻辑
        StandardApplicationElementMetadataRepository elementMetadataRepository = (StandardApplicationElementMetadataRepository) configuration.getElementMetadataRepository();
        elementMetadataRepository.registerFlowElement(HelloTask.class);
        elementMetadataRepository.registerFlowElement(PrintTask.class);

        // 注册流程
        configuration.getLogicFlowRepository().registerLogicFlow(createLogicFlowDefinition());

        // 执行流程
        LogicServiceProvider.initialize(configuration);
        LogicSession session = LogicServiceProvider.getInstance().getLogicRuntime().createSession("test");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "alice");
        Object result = session.execute(parameters);
    }

    private LogicFlowDefinition createLogicFlowDefinition() throws Exception {
        // 流程定义
        LogicFlowDefinition definition = new LogicFlowDefinition();
        definition.setFlowId("test");
        definition.setVersion(1);

        // 流程节点定义
        definition.setFlowElements(Lists.newArrayList(
                createElement("test_start", "start", new ApplicationElementKey("im_start")),

                mapping(createElement("test_hello", "hello", new ApplicationElementKey("sample_hello")), Lists.newArrayList(
                        createMapping("country", "$const/country", "//country"),
                        createMapping("name", "$input/name", "//name")
                )),

                mapping(createElement("test_print", "print", new ApplicationElementKey("sample_print")), Lists.newArrayList(
                        createMapping("name", "$input/name", "//name"),
                        createMapping("city", "$const/city", "//city"),
                        createMapping("message", "hello/message", "//message")
                )),

                mapping(createElement("test_end", "end", new ApplicationElementKey("im_end")), Lists.newArrayList(
                        createMapping("all", "print", "/")
                )),

                connect("s1", "test_start", "test_hello"),
                connect("s2", "test_hello", "test_print"),
                connect("s3", "test_print", "test_end")
        ));

        // 常量
        LogicFlowConstants constants = new LogicFlowConstants();
        constants.setDataDefinition(new JavaBeansDataDefinitionBuilder().type(Map.class).build());
        constants.setData(ImmutableMap.of(
                "city", "beijing",
                "country", "cn"
        ));
        definition.setConstants(constants);

        // 输入结构
        definition.setInputDataDefinition(new JavaBeansDataDefinitionBuilder().type(Map.class).build());
        definition.setOutputDataDefinition(new JavaBeansDataDefinitionBuilder().type(Map.class).build());

        return definition;
    }

    private LogicFlowElementDefinition mapping(LogicFlowElementDefinition definition, List<MappingRule> mappingRules) {
        MappingDefinition mappingDefinition = new StandardMappingDefinition();
        mappingDefinition.getMappingRules().addAll(mappingRules);
        definition.setMappingDefinition(mappingDefinition);
        return definition;
    }

    private MappingRule createMapping(String id, String source, String target) {
        StandardMappingRule rule = new StandardMappingRule();
        rule.setId(id);
        rule.setSource(new StandardValue(new StandardPath(source)));
        rule.setTarget(new StandardPath(target));
        return rule;
    }

    private LogicFlowElementDefinition createElement(String executeId, String alias, ElementKey key) {
        LogicFlowElementDefinition definition = new LogicFlowElementDefinition();
        definition.setExecuteId(executeId);
        definition.setAlias(alias);
        definition.setKey(key);
        return definition;
    }

    private LogicFlowElementDefinition connect(String id, String startPoint, String endPoint) {
        LogicFlowElementDefinition definition = createElement(id, id, new ApplicationElementKey("im_sequence"));
        definition.setProperties(ImmutableMap.of("startPoint", startPoint, "endPoint", endPoint));
        return definition;
    }
}

package simulator.scenario;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulator.scenario.phase.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author kangmoo Heo
 */
public class ScenarioBuilder {
    private static final Logger log = getLogger(ScenarioBuilder.class);
    private static DocumentBuilder documentbuilder;

    private ScenarioBuilder(){

    }

    static {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            dbFactory.setValidating(true);
            dbFactory.setNamespaceAware(true);
            dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
            dbFactory.setFeature("http://xml.org/sax/features/validation", false);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbFactory.setIgnoringElementContentWhitespace(true);
            dbFactory.setIgnoringComments(true);
            dbFactory.setCoalescing(true);
            documentbuilder = dbFactory.newDocumentBuilder();
        } catch (Exception e) {
            log.error("Fail to make document builder", e);
            System.exit(-1);
        }
    }

    public static Scenario buildScenario(File xmlFile) throws IOException, SAXException {
        Document doc = documentbuilder.parse(xmlFile);
        Element element = doc.getDocumentElement();

        Scenario scenario = new Scenario();
        scenario.setName(Optional.ofNullable(element.getAttributes()).map(o -> o.getNamedItem("name")).map(Node::getTextContent).orElse(null));
        nodeList2Stream(element.getChildNodes()).forEach(node -> {
            switch (node.getNodeName()) {
                case "tts":
                    scenario.getPhases().add(new TtsPhase(scenario, node.getTextContent()));
                    break;
                case "stt":
                    scenario.getPhases().add(new SttPhase(scenario, node.getTextContent(),
                            Integer.parseInt(Optional.of(node.getAttributes()).map(o -> o.getNamedItem("duration")).map(Node::getTextContent).orElse("5000")),
                            Boolean.parseBoolean(Optional.of(node.getAttributes()).map(o -> o.getNamedItem("digit")).map(Node::getTextContent).orElse("false"))));
                    break;
                case "mark":
                    scenario.getPhases().add(new MarkPhase(scenario, node.getTextContent()));
                    break;
                case "run":
                    scenario.getPhases().add(new RunPhase(scenario, node.getTextContent()));
                    break;
                case "jump":
                    scenario.getPhases().add(new JumpPhase(scenario, node.getTextContent()));
                    break;
                case "if":
                    scenario.getPhases().add(new IfPhase(scenario,
                            nodeList2Stream(node.getChildNodes()).filter(o -> o.getNodeName().equals("expression")).map(Node::getTextContent).findFirst().orElse(null),
                            new JumpPhase(scenario, nodeList2Stream(node.getChildNodes()).filter(o -> o.getNodeName().equals("jump")).map(Node::getTextContent).findFirst().orElse(null))));
                    break;
                case "sleep":
                    scenario.getPhases().add(new SleepPhase(scenario, Long.parseLong(node.getTextContent())));
                    break;
                default:
                    break;
            }
        });
        return scenario;
    }

    public static Stream<Node> nodeList2Stream(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
    }

    public static List<Node> nodeListMapping(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item).collect(Collectors.toList());
    }
}

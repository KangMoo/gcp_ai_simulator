/**
 * @author kangmoo Heo
 */

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulator.scenario.Scenario;
import simulator.scenario.phase.SleepPhase;
import simulator.scenario.phase.SttPhase;
import simulator.scenario.phase.TtsPhase;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class XmlToObject {
    public static void main(String[] args) {
        try {
            File file = new File("/Users/heokangmoo/Downloads/aisim/question.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Question que = (Question) jaxbUnmarshaller.unmarshal(file);

            System.out.println(que.getId() + " " + que.getQuestionname());
            System.out.println("Answers:");
            List<Answer> list = que.getAnswers();
            for (Answer ans : list)
                System.out.println(ans.getId() + " " + ans.getAnswername() + "  " + ans.getPostedby());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
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
            DocumentBuilder documentbuilder = dbFactory.newDocumentBuilder();

            Document doc = documentbuilder.parse(new File("/Users/heokangmoo/Downloads/aisim/scenario.xml"));
            Element element = doc.getDocumentElement();
            NodeList childNodes = element.getChildNodes();

            Unmarshaller ttsUnmarshaller = JAXBContext.newInstance(TtsPhase.class).createUnmarshaller();
            Unmarshaller sttUnmarshaller = JAXBContext.newInstance(SttPhase.class).createUnmarshaller();
            Unmarshaller sleepUnmarshaller = JAXBContext.newInstance(SleepPhase.class).createUnmarshaller();

            Scenario scenario = new Scenario();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                switch (node.getNodeName()) {
                    case "tts":
                        scenario.getPhases().add((TtsPhase) ttsUnmarshaller.unmarshal(node));
                        break;
                    case "stt":
                        scenario.getPhases().add((SttPhase) sttUnmarshaller.unmarshal(node));
                        break;
                    case "sleep":
                        scenario.getPhases().add((SleepPhase) sleepUnmarshaller.unmarshal(node));
                        break;
                }
            }

            System.out.println(scenario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

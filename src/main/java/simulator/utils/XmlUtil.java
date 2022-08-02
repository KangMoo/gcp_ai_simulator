package simulator.utils;

import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author kangmoo Heo
 */
public class XmlUtil {
    private static final Logger log = getLogger(XmlUtil.class);
    @Getter
    private static DocumentBuilder documentbuilder;

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
            log.warn("Fail to build documentBuilder", e);
        }
    }

    public static Optional<Document> parse(String xml) {
        try (InputStream xmlStream = new ByteArrayInputStream(xml.getBytes())) {
            return Optional.of(new SAXReader().read(xmlStream));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * item : <item>요소를 모두 선택함 <br/>
     * /item : "/" 루트 노드의 자식 노드중에서 <item>엘리먼트를 선택함 (앞에 "/"가 들어가면 절대 경로)<br/>
     * item/jeongpro : <item>엘리먼트의 자식 노드중에서 <jeongpro>엘리먼트를 선택 (상대 경로)<br/>
     * // : 현재 노드의 위치와 상관없이 지정된 노드부터 탐색<br/>
     * //item : 위치와 상관없이 엘리먼트 이름이 <item>인 모든 엘리먼트<br/>
     * item/@id : 모든 <item>엘리먼트의 id속성 노드를 모두 선택함<br/>
     * item[k] : <item>엘리먼트 중에서 k번 째 <item>엘리먼트<br/>
     * item[@attr = val] : attr이라는 속성이 val값을 가지는 모든 <item>엘리먼트
     */
    public static Node selectSingleNode(String xml, String xpathExpression) {
        return parse(xml).map(o -> o.selectSingleNode(xpathExpression)).orElse(null);
    }

    public static List<Node> selectNodes(String xml, String xpathExpression) {
        return parse(xml).map(o -> o.selectNodes(xpathExpression)).orElse(null);
    }

    public static Stream<org.w3c.dom.Node> nodeList2Stream(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
    }

    public static Stream<org.w3c.dom.Node> nodeList2Stream(org.w3c.dom.Node node) {
        return IntStream.range(0, node.getChildNodes().getLength()).mapToObj(node.getChildNodes()::item);
    }
}

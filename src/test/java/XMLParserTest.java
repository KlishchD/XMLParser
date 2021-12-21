import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

public class XMLParserTest {

    @Test(expected = FileNotFoundException.class)
    public void getNormalizedDocumentOfPage_wrongFilePath_ThrowsException() throws Exception {
        XMLParser parser = spy(new XMLParser("sdf.xml"));
        when(parser, "getNormalizedDocumentOfPage");

    }

    @Test
    public void getNormalizedDocumentOfPage_rightFilePath_Document() throws Exception {
        String filePath = "src/main/resources/test.xml";

        XMLParser parser = new XMLParser(filePath);
        Document actual = Whitebox.invokeMethod(parser, "getNormalizedDocumentOfPage");

        assertEquals("file:/Users/dklishch/Documents/IntelliJ%20Idea%20Projects/XMLParser/src/main/resources/test.xml", actual.getDocumentURI());
    }

    @Test
    public void getStudentFromNode_rightNode_Student() throws Exception {
        Student expected = new Student(1, "dinkar", "kad", "dinkar", 85);

        String filePath = "src/main/resources/test.xml";
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(filePath);
        document.normalize();

        XMLParser parser = new XMLParser(filePath);
        Student actual = Whitebox.invokeMethod(parser, "getStudentFromNode", document.getElementsByTagName("student").item(0));

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getStudentFromNode_wrongNode_ThrowsNullPointerException() throws Exception {
        String filePath = "src/main/resources/test.xml";

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(filePath);
        document.normalize();

        XMLParser parser = new XMLParser(filePath);

        Whitebox.invokeMethod(parser, "getStudentFromNode", document.getParentNode());
    }

    @Test(expected = FileNotFoundException.class)
    public void parse_WrongFilePath_ThrowsFileNotFoundException() throws ParserConfigurationException, IOException, SAXException {
        new XMLParser("dfs").parse();
    }

    @Test
    public void parse_RightFilePath_ReturnsStudentsList() throws ParserConfigurationException, IOException, SAXException {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(1, "dinkar", "kad", "dinkar", 85));
        expected.add(new Student(2, "Vaneet", "Gupta", "vinni", 95));
        expected.add(new Student(3, "jasvir", "singn", "jazz", 90));

        String filePath = "src/main/resources/test.xml";
        List<Student> actual = new XMLParser(filePath).parse();

        assertEquals(expected, actual);
    }
}

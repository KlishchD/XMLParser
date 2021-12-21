import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private String filePath;

    public XMLParser() {

    }
    public XMLParser(String filePath) {
        this.filePath = filePath;
    }

    public List<Student> parse () throws ParserConfigurationException, IOException, SAXException {
        List<Student> students = new ArrayList<>();
        NodeList studentNodeList = getNormalizedDocumentOfPage().getElementsByTagName("student");

        for (int i = 0; i < studentNodeList.getLength(); ++i) {
            students.add(getStudentFromNode(studentNodeList.item(i)));
        }

        return students;
    }

    private Document getNormalizedDocumentOfPage() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(filePath));
        document.getDocumentElement().normalize();

        return document;
    }

    private Student getStudentFromNode(Node studentNode) {
        Element element = (Element) studentNode;

        Student student = new Student();
        student.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
        student.setFirstName(element.getElementsByTagName("firstname").item(0).getTextContent());
        student.setLastName(element.getElementsByTagName("lastname").item(0).getTextContent());
        student.setNickname(element.getElementsByTagName("nickname").item(0).getTextContent());
        student.setMarks(Integer.parseInt(element.getElementsByTagName("marks").item(0).getTextContent()));

        return student;
    }

    public List<Student> parse(String filePath) throws ParserConfigurationException, IOException, SAXException {
        return new XMLParser(filePath).parse();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}

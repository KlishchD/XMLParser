import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        XMLParser parser = new XMLParser("src/main/resources/test.xml");
        for (Student student : parser.parse()) {
            System.out.println(student);
        }

    }
}

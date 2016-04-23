import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class htmlParser_jso {

	// declare the capital array and state array
	ArrayList<String> capitalsArray = new ArrayList<String>();
	ArrayList<String> statesArray = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		htmlParser_jso htmlParser = new htmlParser_jso();
		
        Scanner scanner=new Scanner(System.in);
		
		System.out.print("Please enter the html file name:");
		
		String filename=scanner.next();
		
		// get the file instance
		File input = new File("src/"+filename);
		
		// extract the document from the file
		Document doc = Jsoup.parse(input, "UTF-8", "");
		// get capital array
		htmlParser.setCapitals(doc);
		// get state array
		htmlParser.setStates(doc);
		// generate JSON file
		JsonFactory jsonFactory = new JsonFactory();
		try {
			 
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(new File("Output/result.json"), JsonEncoding.UTF8);
			
			// start generate JSON 
			jsonGenerator.writeStartObject();
			
			// start the capitals field
			jsonGenerator.writeArrayFieldStart("capitals");
			
			// captialArray should have the same size as stateArray
			int numOfCapital = 0;
			int numOfState = 0;
			for(numOfCapital = 0; numOfCapital < htmlParser.capitalsArray.size(); numOfCapital++){
				numOfState = numOfCapital;
				jsonGenerator.writeStartObject();
				jsonGenerator.writeStringField("capital", htmlParser.capitalsArray.get(numOfCapital));
				jsonGenerator.writeStringField("state", htmlParser.statesArray.get(numOfState));
				jsonGenerator.writeEndObject();
			}
			// end the capitals field
			jsonGenerator.writeEndArray();
			
			// start the summary object
			
			jsonGenerator.writeObjectFieldStart("summary");
			jsonGenerator.writeNumberField("numberOfCapitals", htmlParser.capitalsArray.size());
			
			// end the object
			jsonGenerator.writeEndObject();
			// close the JSON
	        jsonGenerator.close(); 
	        } catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	public void setCapitals(Document doc){

		Element mainDiv = doc.getElementById("main"); 
			
		Elements capitals = mainDiv.getElementsByClass("capital"); 
		
		String capitalResult;
		
		for(Element capital: capitals){
			// get rid of spaces and line break
			capitalResult = capital.text();
			capitalResult.replaceAll("\\s", "");
			capitalsArray.add(capitalResult);
		}
		
		
	}

	public void setStates(Document doc){
		Element mainDiv = doc.getElementById("main"); 
		
		Elements states = mainDiv.getElementsByClass("state"); 
		
		String stateResult;
		
		for(Element state: states){
			// get rid of spaces and line break
			stateResult = state.text();
			stateResult.replaceAll("\\s", "");
			statesArray.add(stateResult);
		}
		
		
	}

		
		
	}
	


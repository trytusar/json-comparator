# JSON Comparator Library
A simple library which will generate JSON diff and JSON patched valued. json-patch library is used to generate the diff patch and json patch. 
It takes four types of arguments: File, JsonString, Java Object and JsonNode

Add library to your project

Install the jar file to local maven repo. Present inside target directory.

    mvn install:install-file -Dfile=json-comparator.jar -DgroupId=com.json.comparator -DartifactId=json-comparator -Dversion=0.0.1 -Dpackaging=jar 

Add dependency in pom.xml

    <dependency>
        <groupId>com.json.comparator</groupId>
        <artifactId>json-comparator</artifactId>
        <version>0.0.1</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.10.1</version>
    </dependency>
      
      
 Methods present in class JsonCompareUtil:
  
    public static Optional<JsonDiffResponse> compareJsonFiles(File file1, File file2);
    public static Optional<JsonDiffResponse> compareJsonString(String json1, String json2);
    public static Optional<JsonDiffResponse> compareJsonObject(Class<?> obj1, Class<?> obj2);
    public static Optional<JsonDiffResponse> compareJsonNode(JsonNode jsonNode1, JsonNode jsonNode2);
    
 Usage:
 Sample json files are present inside resources directory.
    
    /**
     * Test Application
     *
     */
    public class App {

      String file1Path = ".../resources/file1.json";
      String file2Path = ".../resources/file2.json";

      public static void main(String[] args) {
        App app = new App();
        app.readJson();
      }

      public void readJson() {

        File file1 = new File(file1Path);
        File file2 = new File(file2Path);

        Optional<JsonDiffResponse> diffOptional = JsonCompareUtil.compareJsonFiles(file1, file2);
        if (diffOptional.isPresent()) {
          JsonDiffResponse diffResponse = diffOptional.get();
          System.out.println("Patched Diff:" + diffResponse.getPatchNode());
          System.out.println("Patched JSON:" + diffResponse.getPatchedJson());
        } else {
          System.out.println("Patched Diff failed ");
        }

      }

    }

Output:

    Patched Diff: [{"op":"remove","path":"/company"},{"op":"add","path":"/dummy","value":"Sample"},{"op":"add","path":"/name/nickName","value":"Max Plank"},{"op":"replace","path":"/phone/0/number","value":"99xxxxxx"}]
    Patched JSON: {"name":{"first":"john","lst":"Doe","nickName":"Max Plank"},"address":null,"birthday":"1980-01-01","occupation":"Software Enggineer","phone":[{"number":"99xxxxxx","type":"home"},{"number":"9999999999","type":"mobile"}],"dummy":"Sample"}

# PN-F24lib
Java library that allows you to generate PDF documents for
F24 forms. This library also provides validation of input data.

## Features
* Validation of input data (DTO objects).
* Ability to generate filled PDF documents for next F24 forms: Excise (Accise), Simplified (Semplificato), ELIDE, Standard (IMU).

## Installation

To use PN-F24lib library in your Java project, you can follow next steps:
1. Download the JAR file from the GitHub repository.
2. Add the JAR file to your project's classpath.

If you are using Maven or Gradle, you can add this library as a dependency to your project.

## Usage

To generate PDF documents from DTO objects for F24 forms follow these steps:
1. Import required classes from the library:
   ```java
   import org.f24.dto.form.*;
   import org.f24.exception.ResourceException;
   import org.f24.service.pdf.PDFCreatorFactory;
   import org.f24.service.validator.Validator;
   import org.f24.service.validator.ValidatorFactory;
   ```
2. Create a 'Validator' instance for inheritor of 'F24Form' DTO using the 'ValidatorFactory'
```java
    Validator validator = ValidatorFactory.createValidator(f24Form);
```
3. Validate DTO with 'Validator' instance
```java
    validator.validate();
```
4. Create a 'FormPDFCreator' instance for inheritor of 'F24Form' DTO using the 'PDFCreatorFactory'
```java
    FormPDFCreator pdfCreator = PDFCreatorFactory.createPDFCreator(f24Form);
```
5. Create pdf with 'FormPDFCreator' instance
```java
    byte[] f24Pdf = pdfCreator.createPDF();
```
## Example

Here is simple example how to use PN-F24lib to generate PDF for Simplified (Semplificato) form from JSON object:

```java
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreatorFactory;
import org.f24.service.pdf.impl.FormPDFCreator;
import org.f24.service.validator.Validator;
import org.f24.service.validator.ValidatorFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class F24Generator {
   public static void main(String[] args) throws IOException, ResourceException {
      String simplifiedJson = "resources/input/f24simplified.json";
      String simplifiedString = new String(Files.readAllBytes(Paths.get(simplifiedJson)));

      F24Simplified f24Simplified = new ObjectMapper().readValue(simplifiedString, F24Simplified.class);
      Validator simplifiedValidator = ValidatorFactory.createValidator(f24Simplified);
      simplifiedValidator.validate();

      FormPDFCreator simplifiedCreator = PDFCreatorFactory.createPDFCreator(f24Simplified);
      byte[] simplifiedPdf = simplifiedCreator.createPDF();
      Files.write(Path.of("resources/output/simplified.pdf"),simplifiedPdf );
   }
}
```
## More details

More information and API documentation can be found at https://

## Support

Please enter an issue in the repo for any questions or problems.
Alternatively, please contact us at ...@support.com
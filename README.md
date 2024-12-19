# Receipt-Processor

##    Setup Instructions
######  • Follow these steps to get the Receipt Processor web service up and running in Docker.

#### 1) Clone the Project
######  • Clone the repository using cmd:
            git clone <your-repository-url>

#### 2) Build the Application
######  • Once you have the project cloned, navigate to the project directory and build the application using Maven.
######  • Open the command prompt in the project directory.
######  • Run the following command to compile the code and package it into a JAR file:
            mvn clean package
######  • This will generate the JAR file in the **target/** directory.

#### 3) Build the Docker Image
######  • After the JAR file is created, build the Docker image with the following command:
            docker build -t receiptprocessor .
######  • This will create a Docker image named receiptprocessor.

#### 4) Run the Docker Container
######  • Once the Docker image is built, you can run the application in a Docker container. Use the following command:
            docker run -p 8081:8080 receiptprocessor:latest
######  • This will start the application in the container, and it will be accessible at **http://localhost:8081**.

#### 5) Test the API with Postman
######  • Once the application is running, you can use **Postman** to test the API.

#####  POST Request - Process Receipt
######  **URL:** http://localhost:8081/receipts/process
######  **Method:** POST
######  **Body:** Use the following JSON body to test the receipt processing endpoint:

```json
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },
    {
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },
    {
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },
    {
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },
    {
      "shortDescription": "Klarbrunn 12-PK 12 FL OZ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
```

######  • This will send a POST request to process the receipt and return an id.

##### GET Request - Get Points for a Receipt
######  • Once you have the receipt ID from the POST request, you can use the following GET request to retrieve the points for the processed receipt.

######  **URL:** http://localhost:8081/receipts/{id}/points
######  Replace {id} with the actual receipt ID you received from the POST request.

## Additional Notes
######  • Data Persistence: Data does not persist after the application is stopped. The application stores data in memory, meaning that when the container is stopped or restarted, all data will be lost.
######  • Port Mapping: The application is exposed on port 8080 inside the container, but you map it to port 8081 on your local machine using -p 8081:8080 in the docker run command.
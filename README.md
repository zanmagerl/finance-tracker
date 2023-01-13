# Spending Tracker

This project provides simple function implementation that enables user's to write spending data to BigQuery. For request authentication we use Firebase.

To build project run:
```
./gradlew build
```

To deploy function to GCP Cloud Function (Google's FaaS environment):
```
gcloud functions deploy spending-tracker  
    --entry-point=io.quarkus.gcp.functions.QuarkusHttpFunction
    --runtime=java17
    --trigger-http
    --allow-unauthenticated
    --source=build/deployment
```
package com.seemenu;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Lambda handler for Spring Boot application
 * Uses AWS Serverless Java Container to run Spring Boot in Lambda
 */
public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            // Initialize Spring Boot application
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(SeeMenuApplication.class);

            // Strip the API Gateway stage from the path
            handler.stripBasePath("/prod");

            // Enable binary content support for multipart uploads
            // This is critical for API Gateway to properly handle file uploads
            handler.activateSpringProfiles("lambda");

        } catch (ContainerInitializationException e) {
            // If we fail to initialize, rethrow as runtime
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}

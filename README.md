# Assignment: URL Shortener

## Description

This application provides the ability to shortener URL and provide the user with a shortened link.

## Mandatory Requirements

- Design and implement an API for short URL creation
- Implement forwarding of short URLs to the original ones
- There should be some form of persistent storage

## Technologies

- Java 11
- Spring Boot 2.4.2
- JUnit 4

## Examples for each call
POST http://localhost:8080?url={url} 

GET http://localhost:8080/{key}
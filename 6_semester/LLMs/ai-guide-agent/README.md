# ai-guide-agent
check ReadMe from both LLM and TravelTalk folders for setup 

This project creates an AI-powered city guide app that provides real-time, personalized audio descriptions through headphones as users explore a city. It leverages vector databases, AI agents, RAG (Retrieval-Augmented Generation), embeddings, LLMs (Large Language Models), and advanced speech-to-text capabilities.

## Project Overview

This app gathers, processes, and delivers relevant information based on the user's location, preferences, and history, offering a seamless, informative city exploration experience.

## Architecture
Mobile App Programming Language: React Native + Expo Go<br>
Dataset Selection: Data from Wikipedia or Custom Dataset from Generated Answers from another LLM: We store some data already scraped from wikipedia in a vector database, or generated with another LLM information and store that in the vector database<br>
Chunking Strategy: Semantic Chunking (Sliding Window Chunking)<br>
Vector Database: ChromaDB<br>
Model Choices: DeepSeek-R1-Distill-Qwen-7B (generating description), all-MiniLM-L6-v2 (embeddings), Sesame (Text-to-Speech)<br>
RAG Strategies to Try: RAG by Source (Branched), RAG with Memory<br>
RAG By Source: Having a hybrid method where we check if we have stored data about the location in our vector database and if not we use an api (probably wikipedia) to gather information.<br>
RAG with Memory: Making it so that the LLM has memory of the user’s previous prompts and takes them into acount when providing new answers.<br>

# ai-guide-agent

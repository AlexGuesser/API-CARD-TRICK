# API-CARD-TRICK

Welcome to API-CARD-TRICK!

This API Rest built with Spring Boot will allow you to do a card magic (actually is a simples math trick) wich you will discover the card of someone among a 21 cards deck. 

To Run this API  just dowload the project, descompt and open as a Maven project. After that, create a database with name db_card_trick at your MySQL connection, and configure the application.properties of this project with your url,user and password.

This API has two end-points. The first is "/game" where using GET you will receive a deck with its "deckId", and three cards piles with seven cards each. Pick a card, memorize it, and go to "/plays". At "/plays", using POST, you have to inform your move on RequestBody with three parameters: deckId,round and pile. After three "/plays", sending corrects deckId,round and pile, you will visualize the code of the chosen card at "yourCardIs".

Good Game!

Alex Guesser

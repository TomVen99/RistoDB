# How to use
Una volta scaricato il database, il nome impostato di default è *ristoDb*, se si dovesse modificare il nome occorre impostare il nome scelto nel file *src/main/java/it/unibo/ristodb/view/LoginController.java* file.
````
private static final String DB_NAME = "RistoDb";
````
Username e password verranno chieste all'accesso tramite form di login. Le credenziali predefinite per la connessione al database sono user: admin e password: admin perciò sarà necessario creare un account sul database con quelle credenziali oppure andare a modificare i parametri. Riga 35 di LoginController.java
Per l'inizializzazione del database sono presenti due file dentro la cartella initDB. Il file createTable.sql permette di creare il database mentre initializeDB.sql popola il database con le righe essenziali per poter ustilizzare l'applicazione.
Per far partire l'applicazione sarà sufficiente eseguire il file *src/main/java/it/unibo/ristoDB/RistoDB.java*.

package sample;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Database {

    public static int QUIZ_NUMBER = 4; //real quiz number = QUIZ_NUMBER + 1

    public static int LESSONS_NUMBER = 3; //real number

    public static int FRAMES = 10;

    public List<String>[] Lessons = new List[LESSONS_NUMBER + 1]; // + animation DISCLAIMER: animation has no text, this lesson is left for further additions

    public List<String>[] Questions = new List[LESSONS_NUMBER];

    public List<String>[] Options = new List[LESSONS_NUMBER];

    public ArrayList<Integer>[] CorrectAnswer = new ArrayList[LESSONS_NUMBER];

    public ArrayList<Boolean>[] QuizDone = new ArrayList[LESSONS_NUMBER ];

    public String[] Titles = new String[LESSONS_NUMBER + 1];

    public String[] Keys = new String[FRAMES];

    public String[] HashValues = new String[FRAMES];

    public String[] Scans = new String[FRAMES];

    public Image[] frames = new Image[FRAMES];

    public Boolean bool1 = true, bool2 = false, bool3 = false, bool4 = false;

    public Image hash;

    public Random random;


    public Database() {
        random = new Random();

//titles

        Titles[0] = ("Tabelle Hash");
        Titles[1] = ("Funzioni Hash");
        Titles[2] = ("Metodi di scansione");
        Titles[3] = ("Complessita'");

//lessons

        Lessons[0] = Stream.of(
                "Le tabelle hash sono un metodo per la realizzazione di dizionari e insiemi.\n" +
                        "L idea è quella di ricavare la posizione della chiave in memoria basandosi sulla chiave stessa, questa operazione ha costo medio O(1) mentre nel caso pessimo ha costo O(n).\n" +
                        "Sia U l universo delle chiavi e A un vettore di dimensione m con indici compresi tra 0 e m-1. La soluzione ideale è quella di possedere una funzione iniettiva e che quindi per ogni chiave abbiamo una posizione nel vettore A e che quindi per ogni k1 != k2 H(k1) != H(k2) con k1 e k2 appartenenti a U.\n" +
                        "Per garantire l’iniettività della funzione m non può essere più piccola di |U|, questo meccanismo prende il nome di tabella ad accesso diretto.\n" +
                        "Questo sistema è utilizzabile solo se U è un insieme relativamente piccolo altrimenti comporterebbe un enorme spreco di memoria.\n",
                "La dimensione m del vettore deve quindi essere una sovrastima del numero di chiavi che verranno utilizzate contemporaneamente cosi da avere sempre lo spazio necessario e cosi da non creare degli agglomerati nel vettore.\n" +
                        "Si crea quindi un ulteriore problema che è quello delle collisioni, le collisioni avvengono quando 2 chiavi diverse messe dentro una funzione hash danno come output lo stesso intero e quindi vorrebbero stare entrambe nella stessa posizione.\n",
                "In conclusione:\n" +
                        "per la realizzazione di una tabella hash efficiente abbiamo bisogno di:\n" +
                        "1.Una funzione hash che distribuisca in modo uniforme  le chiavi nel vettore e che tenga il numero di collisioni il più basso possibile.\n" +
                        "2.Un metodo di scansione efficiente.\n" +
                        "3.La dimensione m del vettore A deve essere una sovrastima del numero di chiavi attese."
        ).collect(toList());

        Lessons[1] = Stream.of(
                "Trovare una buona funzione hash non è semplice, una funzione hash deve prendere in input una chiave e dare in output un numero più casuale possibile ovviamente che sia sempre lo stesso per ogni chiave altrimenti non si sarebbe in grado di trovare la chiave in futuro.",
                "Per definire funzioni hash è conveniente considerare la rappresentazione binaria della chiave.\n" +
                        "Esempio:\n" +
                        "numero -> rappresentazione binaria\n" +
                        "parola ->rappresentazione binaria per ogni carattere\n" +
                        "Questo è utile non solo perché ci permette di visualizzare in binario una chiave alfanumerica ma ci permette anche di trasformare facilmente da binario in un numero intero.\n" +
                        "\n",
                "Qui elenchiamo 4 metodi di generazione di indirizzi hash:\n" +
                        "Estrazione: si estrae un sottoinsieme b di p bit dal codice binario, solitamente estratto nelle posizioni centrali, m deve essere scelto opportunamente.\n" +
                        "Xor: l indirizzo è dato dalla somma modulo 2 effettuata bit a bit di diversi sottoinsiemi di p bit del codice binario della chiave, m deve essere scelto opportunamente rispetto a p.\n" +
                        "Moltiplicazione: dove l indirizzo è [m(iC-[iC])], dove m è un numero qualsiasi, i è l intero dato dal codice binario della chiave e C è un numero reale compreso tra 0 e 1.\n" +
                        "Divisione: l indirizzo è uguale al resto della divisione dell intero dato dal codice binario della chiave per m, p deve essere scelto opportunamente.\n"
        ).collect(toList());

        Lessons[2] = Stream.of(
                "I metodi di scansione si distinguono in esterni ed interni, a seconda che le chiavi siano memorizzate all interno o all esterno del vettore A.\n" +
                        "Il metodo esterno per eccellenza è quello delle liste di trabocco, è molto semplice ed efficiente ma richiede l implementazione di liste collegate alla tabella principale, i metodi interni possono essere la scansione lineare, la scansione quadratica, la scansione pseudocasuale e l hashing doppio, che non richiedo l implementazione di altre strutture dati poiché sono sviluppate tutte sullo stesso vettore.\n",
                "Per la scansione interna viene utilizzata una tecnica con un indice i aggiuntivo dove H(k, i) è la posizione della chiave k dopo i ispezioni fallite,\n é importante che la funzione H(k, i) tocchi tutte le posizioni m del vettore A una e una sola volta cosi se il vettore A è completamente pieno la ricerca si arresta quando viene toccato per la seconda volta la posizione di partenza H(k).\n",
                        "Scansione lineare: (H(k, i) = (H(k) + h * i) mod m) Si utilizza un parametro h che rappresenta la distanza tra 2 successive posizioni esaminate, questo metodo ha lo svantaggio di non ridurre la formazione di agglomerati di chiavi, infatti se 2 chiavi k’ e k hanno indirizzi hash H(k’) = H(k)+h le due sequenze di scansione collidono per ogni multiplo di h. questo tipo di problema prende il nome di agglomerazione primaria.\n",
                "Scansione quadratica: H(k, i) = (H(k) + h * i2)mod m \nIn questo metodo m è un numero primo, l’ elevazione al quadrato di i riduce l’ effetto di agglomerazione lineare poiché la distanza tra 2 posizioni successive non è costante ma variabile. Qui il problema di agglomerazione primaria non è presente ma se abbiamo 2 chiavi con indirizzi hash iniziali uguali il problema rimane, questo problema prende il nome di agglomerazione secondaria.",
                "Scansione pseudocasuale: H(k, i) = (H(k) + ri)mod m  \nIn questo metodo ri è l i-esimo numero generato da un generatore di numeri pseudo-casuali tale per cui la sequenza ri è una permutazione della sequenza m, in questo metodo rimane il problema dell agglomerazione secondaria.\n",
                "Hashing doppio: H(k, i) = (H(k) + i * H’(k))mod m  \nIn questo metodo vengono utilizzate 2 funzioni hash nello stesso metodo, in questo modo anche se H(k) = H(k’) la probabilità che anche H’(k)=H’(k’) è molto bassa. Questo evita la formazione di agglomerati sia primari che secondari.                "
        ).collect(toList());
        //not used

        Lessons[3] = Stream.of(
                "Lesson 3 page 1",
                "Lesson 3 page 2",
                "Lesson 3 page 3"
        ).collect(toList());


//questions

        Questions[0] = Stream.of(
                "Facciamo un esempio di funzione hash di divisione della parola 'casa'\n e utilizziamo m=383, int(casa)=924897\n ora calcolare H(casa)=int(casa)mod m.",
                    "H(casa)= 924897 mod 383 = 335",
                "Ora facciamo un esempio di funzione hash di estrazione,\n m=256 e prendiamo i bit dalla posizione 10 alla posizione 17.\n bin(casa)=000011 100001 110011 100001\n ora calcolare H(casa) dal valore binario.",
                    "H(casa)= int(00111001)=57",
                "Nella funzione di estrazione in quale caso avremo 2 indirizzi hash che collidono?",
                    "La funzione di estrazione prende in considerazione i bit centrali della parola\n quindi se questi bit sono uguali l indirizzo hash risultante sarà uguale",
                "Quali tra le funzioni hash che abbiamo visto in precedenza è la più semplice e comoda da usare?",
                    "La funzione di moltiplicazione è la più comoda in quanto ti permette di prendere m a piacere senza essere vincolato da altri parametri",
                "per elaborare una funzione hash efficente dobbiamo partire da delle basi generalizzate,\n cosa dobbiamo assumere se vogliamo che la realizzazione sia ottimale per la maggior parte dei casi?",
                    "In questo modo la funzione avrà una proprietà di uniformità semplice rendendo perfetta la realizzazione di una funzione generale"
        ).collect(toList());

        Questions[1] = Stream.of(
                "Domanda: se un elemento viene eliminato e lo sostituiamo con nil la prossima ricerca di una chiave k potrebbe fallire poiché troverebbe uno spazio vuoto quando invece prima era pieno, qual è la soluzione migliore?",
                    "Piccola spiegazione: utilizzare un label deleted è la soluzione più semplice e veloce e permette di lasciare invariato il procedimento di una futura scansione.\n",
                "Dopo aver visto in grandi linee tutti i metodi di scansione quale è il più efficiente e con meno probabilità di agglomerati?",
                    "Piccola spiegazione della domanda: le liste di trabocco hanno un efficienza migliore e una bassa probabilità di formazione di agglomerati in quanto utilizzano un metodo di concatenamento di più strutture dati",
                "Qual è invece il metodo di scansione più efficiente ma anche più semplice da realizzare?",
                    "Piccola spiegazione: l hashing doppio è il più efficiente tra i metodi di scansione interni ed è il più facile da realizzare perché non richiede la realizzazione di un ulteriore struttura dati.",
                "Pensiamo di implementare un metodo di scansione tramite liste di trabocco, la grandezza del vettore m quanto deve essere per essere abbastanza grande e per contenere tutte le chiavi?",
                    "Piccola spiegazione: le liste di trabocco utilizzano un metodo esterno quindi il vettore è poco utilizzato mentre le liste collegate ad esso sono le più utilizzate, per questo m deve essere uguale alla stima delle chiavi attese.",
                "La funzione H(k, i) per essere funzionale deve:",
                    "In questo modo la ricerca si arresterà quando viene toccata per la seconda volta la posizione di partenza H(k)"
        ).collect(toList());
        //not used

        Questions[2] = Stream.of(
                "Q-Lesson3 page 1",
                    "A-Lesson3 page 1",
                "Q-Lesson3 page 2",
                    "A-Lesson3 page 2",
                "Q-Lesson3 page 3",
                    "A-Lesson3 page 3",
                "Q-Lesson3 page 4",
                    "A-Lesson3 page 4",
                "Q-Lesson3 page 5",
                    "A-Lesson3 page 5"
        ).collect(toList());

 //options

        Options[0] = Stream.of(
                "335",
                "23",
                "9734",
                "57",
                "101",
                "207",
                "Quando le lettere centrali delle due parole sono uguali",
                "Quando la lunghezza delle 2 parole è uguale",
                "Quando le iniziali delle 2 parole sono uguali",
                "Moltiplicazione",
                "Estrazione",
                "XOR",
                "La probabilità che una chiave finisca in un certo punto del vettore sia la stessa per tutte le chiavi",
                "Tutte le chiavi abbiano una dimensione di caratteri minore di un numero n",
                "Non fare particolari assunzioni cosi da rendere l algoritmo più ottimale per un applicazione reale"
        ).collect(toList());
        Options[1] = Stream.of(
                "Utilizzare deleted al posto di nil",
                "Cancellare definitivamente la posizione ",
                "Usare una lista di trabocco in cui memorizzare le chiavi anche se sono state cancellate",
                "Liste di trabocco",
                "Hashing doppio",
                "Scansione pseudo-casuale",
                "Hashing doppio",
                "Liste di trabocco",
                "Scansione lineare",
                "m = una stima di tutte le chiavi attese",
                "m = al doppio delle chiavi attese",
                "m = circa 1/3 di U ovvero di tutte le chiavi possibili",
                "Toccare ogni posizione m del vettore A una e una sola volta",
                "Controllare completamente il vettore ogni volta che si cerca una chiave",
                "Avere un valore di i che sia dispari"
                ).collect(toList());
        //not used

        Options[2] = Stream.of(
                "Page1 Option 1",
                "Page1 Option 2",
                "Page1 Option 3",
                "Page2 Option 1",
                "Page2 Option 2",
                "Page2 Option 3",
                "Page3 Option 1",
                "Page3 Option 2",
                "Page3 Option 3",
                "Page4 Option 1",
                "Page4 Option 2",
                "Page4 Option 3",
                "Page5 Option 1",
                "Page5 Option 2",
                "Page5 Option 3"
                ).collect(toList());





            hash = new Image("sample/hash.png");
            frames[0] = new Image("sample/frame1.png");
            frames[1] = new Image("sample/frame2.png");
            frames[2] = new Image("sample/frame3.png");
            frames[3] = new Image("sample/frame4.png");
            frames[4] = new Image("sample/frame5.png");
            frames[5] = new Image("sample/frame6.png");
            frames[6] = new Image("sample/frame7.png");
            frames[7] = new Image("sample/frame8.png");
            frames[8] = new Image("sample/frame9.png");
            frames[9] = new Image("sample/frame10.png");




        Keys[0] = "Ciao";
        Keys[1] = "Ciao";
        Keys[2] = "Ciao";
        Keys[3] = "Ciao";
        Keys[4] = "Ciao";
        Keys[5] = "Casa";
        Keys[6] = "Casa";
        Keys[7] = "Casa";
        Keys[8] = "Casa";
        Keys[9] = "Casa";

        HashValues[0] = "non calcolato";
        HashValues[1] = "position = 3";
        HashValues[2] = "position = 3";
        HashValues[3] = "position = 3";
        HashValues[4] = "position = 3";
        HashValues[5] = "non calcolato";
        HashValues[6] = "position = 3";
        HashValues[7] = "position = 3";
        HashValues[8] = "position = 3";
        HashValues[9] = "position = 3";

        Scans[0] = "non scansionato";
        Scans[1] = "non scansionato";
        Scans[2] = "HashTableEntry = null";
        Scans[3] = "HashTableEntry = null";
        Scans[4] = "HashTableEntry = null";
        Scans[5] = "non scansionato";
        Scans[6] = "non scansionato";
        Scans[7] = "HashTableEntry = null";
        Scans[8] = "HashTableEntry = null";
        Scans[9] = "HashTableEntry = null";


 // end of database
 // answer sorting algorithm


        for (int i= 0; i < LESSONS_NUMBER; i++) {
            CorrectAnswer[i] = new ArrayList<Integer>();
        }


        for (int i = 0; i < LESSONS_NUMBER; i ++) {
            for (int j = 0; j < 5; j++) {
                int n = random.nextInt(3);
                CorrectAnswer[i].add(j, n);
            }
        }

        for (int i= 0; i < LESSONS_NUMBER; i++) {
            QuizDone[i] = new ArrayList<Boolean>();
        }

        for (int i = 0; i < LESSONS_NUMBER; i ++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0) QuizDone[i].add(true);  //first Lesson has no quiz active, all bool set to true;
                 else QuizDone[i].add(false);
            }
        }
    }
}

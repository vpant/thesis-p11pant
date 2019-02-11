# Πτυχιακή Εργασία
**Όνομα Φοιτητή:** Βασίλειος Παντελής

**Α.Μ.:** Π2011003

**Θέμα πτυχιακής:** Οπτικοποίηση Δεδομένων στο βιντεοπαιχνίδι Minecraft

**Domain:** twittercity.org

# Περιεχόμενα
[Παραδοτέο 1](#Παραδοτέο-1)

[Παραδοτέο 2](#Παραδοτέο-2)

[Παραδοτέο 3](#Παραδοτέο-3)

Παραδοτέο 4

Παραδοτέο 5

Παραδοτέο 6

Παραδοτέο 7

Παραδοτέο 8

Τελικό παραδοτέο

[Χρονοδιάγραμμα](#Χρονοδιάγραμμα-πτυχιακής-εργασίας)

# Παραδοτέο 1

### Τίτλος & περιγραφή πτυχιακής

#### Συλλογή και αποθήκευση δεδομένων από το κοινωνικό δίκτυο Twitter και οπτικοποίηση στον εικονικό κόσμο του βιντεοπαιχνιδιού Minecraft

Η παρούσα πτυχιακή εργασία θα ασχοληθεί με την εξόρυξη δεδομένων από το κοινωνικό δίκτυο Twitter, με την σωστή διαχείριση αυτών (φιλτράρισμα, δόμηση και αποθήκευση)  και την δημιουργία Minecraft mod (συγγραφή κώδικα που θα τροποποιεί, θα προσθέτει ή/και θα αφαιρεί  λειτουργίες του παιχνιδιού). Το όνομα του minecraft mod θα είναι Twitter City και το logo στην ιστοσελίδα της πτυχιακής θα έχει σχέση με αυτήν την ονομασία.

Για την καλή οργάνωση και την εύκολη διαχείριση του πρακτικού μέρους της πτυχιακής θα χωριστεί σε τρία κομμάτια: πρόγραμμα εξόρυξης και διαχείρισης δεδομένων, [Minecraft Mod](https://minecraft.gamepedia.com/Mods/Forge) (οπτικοποίηση των δεδομένων) και ιστοσελίδα πτυχιακής.

Το πρόγραμμα για την εξόρυξη των δεδομένων από το Twitter θα χρησιμοποιεί το [Search API](https://developer.twitter.com/en/docs/tweets/search/overview/basic-search) αναζητώντας tweets με βάση κάποια keywords όπως «χτίζω σπίτι, πόλη κ.α.» (τα παραδείγματα είναι ενδεικτικά και σίγουρα θα τροποποιηθούν κατά την διάρκεια της βελτιστοποίησης ποιότητας και σαφήνειας τον εξορυγμένων tweets). Τα tweets θα αποθηκεύονται σε βάση δεδομένων που θα είναι προσβάσιμη (πιθανόν μέσω ενός Rest API που θα υλοποιηθεί) για να την διαχειριστούν και τα υπόλοιπα κομμάτια της εργασίας.

Συνεχίζοντας, το mod που θα δημιουργηθεί θα έχει σαν κύρια ευθύνη την δημιουργία της πόλης στον κόσμο του Minecraft. Δηλαδή, για κάθε tweet που έχει αποθηκεύσει το πρόγραμμα εξόρυξης δεδομένων από το Twitter ένα [block](http://minecraft.wikia.com/wiki/Blocks) θα χτίζετε στον κόσμο του Minecraft, το οποίο θα "κρατάει" και τις πληροφορίες του Tweet (κείμενο, συγγραφέας, ημερομηνία). Συνδυάζοντας τα blocks θα χτίζονται κατασκευές που θα δημιουργούν μια πόλη. Το mod θα σχεδιαστεί έτσι ώστε να δουλεύει ανεξάρτητα εάν είναι εγκατεστημένο σε Minecraft [Server](https://minecraft.gamepedia.com/Server) ή [Client](https://minecraft.gamepedia.com/Minecraft_launcher). Αυτό σημαίνει ότι θα εξαρτάται μόνο από την βάση δεδομένων που θα είναι αποθηκευμένες όλες οι απαραίτητες πληροφορίες για την σωστή λειτουργία του προγράμματος. Στόχος είναι η πόλη να γίνει μέρος του παιχνιδιού και να μην γίνει μεγάλη αλλαγή στην λογική του.

Η ιστοσελίδα της πτυχιακής θα αντιπροσωπεύει την παρουσίαση του mod και των λειτουργιών του και θα απαρτίζεται από χρήσιμο περιεχόμενο όπως είναι documentation, πληροφορίες για το Rest API και σύνδεσμο στο αποθετήριο στο github που θα περιέχει τον κώδικα της πτυχιακής.

Τέλος, η πτυχιακή εργασία θα απαρτίζεται από το γραπτό κομμάτι που θα περιγράφει αναλυτικά κάθε τομέα της.

# Παραδοτέο 2

#### Εύρεση αναγκαίων εργαλείων, εφαρμογή τους και μία αρχική χρήση. Δημιουργίας λίστας από τις λειτουργίες που θα ήταν επιθυμητό να απαρτίζεται το πρόγραμμα.

Σε αυτή την αναφορά θα ασχοληθώ με τα εργαλεία τα οποία θα χρησιμοποιηθούν για την υλοποίηση της πτυχιακής μου εργασίας, πως θα χρησιμοποιηθούν, ποιές θα είναι οι κύριες λειτουργίες που θα την απαρτίζουν και ορισμένα χαρακτηριστικά για τα γραφικά της εργασίας (ιστοσελίδας, mod) και την ροή - λογική της. 

### Εργαλεία
* [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html): γλώσσα προγραμματισμού.
* [Eclipse IDE](https://www.eclipse.org/): πρόγραμμα ανάπτυξης κώδικα (κυρίως Java).
* [Minecraft Forge API](https://files.minecraftforge.net/): βασική βιβλιοθήκη γραμμένη σε java που επιτρέπει την δημιουργία modifications στο minecraft.
* [Mapcrafter](https://mapcrafter.org/): πρόγραμμα που οπτικοποιεί ένα αρχείο κόσμου του minecraft σε χάρτη προσβάσιμο από browser.
* [Git](https://git-scm.com/): version control στο κώδικα της πτυχιακής και εύκολος συγχρονισμός του στο [Github](https://github.com/).
* [Wordpress](https://wordpress.com/): CMS για δημιουργία ιστοσελίδων.
* VPS από [Okeanos](https://okeanos.grnet.gr/): φιλοξενία της ιστοσελίδας της πτυχιακής και του Minecraft server.

### Προδιαγραφές
* Minecraft Mod:
  * Χτίσιμο της πόλης ανάλογα με το πλήθος των tweets.
  * Πρόσβαση, μέσω κάποιου UI, στα tweets που έχτισαν την πόλη.
* Πρόγραμμα εξόρυξης δεδομένων που φιλτράρει και αποθηκεύει τα δεδομένα (tweets).
* Rest API που θα παρέχει εύκολη πρόσβαση από οποιονδήποτε (μπορεί και εγγραφή αν στο μέλλον είναι απαραίτητο από την εφαρμογή)
* Ιστοσελίδα πτυχιακής που θα αποτελείται:
  * Rendered του map που θα χρησιμοποιεί ο Minecraft Server της πτυχιακής.
  * Documentation του Rest API.
  * Documentation κώδικα.
  * Πληροφορίες για το mod και τις λειτουργίες του.

### Χαρακτηριστικά εργασίας

Στόχος είναι το mod στο Minecraft που θα δημιουργηθεί σε αυτή την εργασία να ενσωματωθεί στην λογική και στα γραφικά του παιχνιδιού και, σε καμία περίπτωση, να μην περιορίσει τον παίχτη. Κάποια κύρια σημεία που θέλουν ιδιαίτερη προσοχή για να το πετύχουμε αυτό είναι:

##### Εμφάνιση και δόμηση (υλικό για τα σπίτια κ.α.) της πόλης
Στόχος μου είναι το τελικό αποτέλεσμα να μοιάζει όσο πιο φυσικό γίνεται, όπως για παράδειγμα σε αυτή την φωτογραφία. 
![2017-12-11_19 02 28](https://user-images.githubusercontent.com/11424758/33942395-77a13f60-e01e-11e7-88de-3931f0ab2bf2.png)
Η αρχική μου σκέψη ήταν να δημιουργώ τα κτήρια προγραμματιστικά block ανά block, εάν και είναι ο τρόπος που διαχειρίζομαι τις κατασκευές στις προηγούμενες εργασίες μου τον θεώρησα λίγο μη επεκτάσιμο και δύσκολα διαχειρίσιμο. Για αυτό μετά από λίγη έρευνα βρήκα κάποια αρχεία με την ονομασία [schematics](https://minecraft.gamepedia.com/Schematic_file_format) τα οποία χρησιμοποιούνται από το community του minecraft που ασχολούνται με moding για την ανταλλαγή κτηρίων, σχεδίων ακόμα και ολόκληρους κόσμους. Οπότε σκέφτομαι να προγραμματίσω ένα reader για τέτοια αρχεία που θα περιέχουν κατασκευές όπως σπίτια και ύστερα θα τα χτίζω βάζοντας προγραμματιστικά στην σωστή θέση.
Επιπλέον βρήκα αυτό το πρόγραμμα το οποίο μπορεί να μετατρέψει 3D μοντέλα σε "blueprints" για minecraft, όπως θα δείτε παρακάτω, και να τα κάνει export σε .schematic αρχεία.

![bb_step1_model](https://user-images.githubusercontent.com/11424758/33944381-afb25cf8-e024-11e7-80cc-f1279f90997e.jpg)
![bb_step2_voxelized](https://user-images.githubusercontent.com/11424758/33944382-afd9624e-e024-11e7-9643-c59cc773958f.jpg)

Ο παραπάνω τρόπος ανοίγει και την πιθανότητα για δυναμική φόρτωση κτηρίων από τους χρήστες αλλά από την άλλη εκφέρει κάποιους τεχνικούς περιορισμούς για αυτό προς το παρόν δεν είναι σίγουρο ότι το mod θα χρησιμοποιεί τέτοια αρχεία για την διαχείριση και το χτίσιμο των κτηρίων.

##### Client-side συγχρονισμός: δυναμική πόλη (διαφορετική για κάθε client)
Για κάθε client, πιο συγκεκριμένα για κάθε world, θα δημιουργείται μια διαφορετική πόλη έτσι ώστε ο χρήστης να έχει την επιλογή να δει μια παραλλαγμένη διαμόρφωσή της ανά δημιουργημένο κόσμο.

##### Textures των blocks 
Στις παλιότερες εργασίες μου χρησιμοποίησα ένα block που δημιούργησα ο ίδιος και σαν texture είχε μία από τις παρακάτω εικόνες (κάθε σπίτι είχε διαφορετικό χρώμα): 

![blue_twitter_block_side](https://user-images.githubusercontent.com/11424758/33946774-53ab20a0-e02b-11e7-8e6c-6f9bd4c3de4a.png) 
![yellow_twitter_block_side](https://user-images.githubusercontent.com/11424758/33946784-56ba2a8e-e02b-11e7-8f6b-74bf5dd1b679.png) 
![green_twitter_block_side](https://user-images.githubusercontent.com/11424758/33946786-5706a3be-e02b-11e7-804b-c1009ab1a5aa.png)

Παρόλα αυτά τα textures δεν φαίνονται αρκετά ωραία όταν τα βλέπεις στο σύνολό τους σε ένα κτήριο και προκαλεί μία αίσθηση μονοτονίας. Στην παρούσα εργασία τα blocks των κτηρίων θα έχουν textures από τα ήδη υπάρχοντα blocks του minecraft όπως Stone, Wood κ.α. Παρακάτω μπορείτε να δείτε τα διαθέσιμα blocks στο Minecraft.

![template1](https://user-images.githubusercontent.com/11424758/36074366-d76d8df6-0f47-11e8-9e1f-11c4bcd62b1b.png)


##### Ιστοσελίδα πτυχιακής

Ως τώρα για την [ιστοσελίδα](https://twittercity.org) της πτυχιακής έχω δημιουργήσει το logo που θα χρησιμοποιήσω. Πιο κάτω θα επισυνάψω μια φωτογραφία του αλλά με λίγα λόγια χρησιμοποίησα ένα περίγραμμα κτηρίων τα οποία μετά από επεξεργασία στο photoshop το περίγραμμα αποτελείται από pixels όπως και τα γραφικά του Minecraft.
![logo2](https://user-images.githubusercontent.com/11424758/36074355-bbc0e5da-0f47-11e8-9f4c-b0e78f26d231.png "Twitter City Logo")



# Παραδοτέο 3

##### Σχεδιασμός και υλοποίηση του script που θα κάνει data mining με δυνατότητα εύκολης παραμετροποίησης των search queries. Σχεδιασμός δόμησης για το Minecraft Mod. Υλοποίηση του mod.

Σε αυτή την αναφορά θα γίνει ανάλυση της δόμησης, των χαρακτηριστικών και της περιγραφής του προγράμματος που θα κάνει εξόρυξη δεδομένων από το Twitter. Επιπλέον, θα παρουσιαστεί ενός αρχικός σχεδιασμός του προγράμματος mod για το Minecraft.

### TwitterDataMiner
##### Συνοπτική περιγραφή
Το πρόγραμμα για την εξόρυξη των δεδομένων ονομάζεται TwitterDataMiner και ο σκοπός της λειτουργίας, περιεκτικά, είναι:

* συνδέεται στο Twitter χρησιμοποιώντας το API ([Search API](https://developer.twitter.com/en/docs/tweets/search/overview/standard "Search API Documentation")) που μας παρέχει το [Twitter](https://twitter.com/ "Twitter")
* αναζητά [tweets](https://en.wikipedia.org/wiki/Twitter#Tweets "Tweets wikipedia") με βάση κάποιες λέξεις κλειδιά που έχουμε ορίσει.
* φιλτράρει τα tweets που έχουν συλλεχθεί με κάποια προκαθορισμένα κριτήρια και απορρίπτει όποιο tweet δεν τα τηρεί.
* τα συλλεγμένα και φιλτραρισμένα δεδομένα αποθηκεύονται στη βάση δεδομένων

##### Αναλυτική και τεχνική περιγραφή
Το πρόγραμμα έχει χωριστεί σε διάφορα κομμάτια το οποίο το καθένα έχει την δική του λειτουργία. Τα σημαντικότερα πακέτα που στα οποία έχουν υλοποιηθεί οι κύριες λειτουργίες είναι τα [database](https://github.com/vpant/thesis-p11pant/tree/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/database "twitterdataminer/database"), [http](https://github.com/vpant/thesis-p11pant/tree/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/http "twitterdataminer/http") και [searchtwitter](https://github.com/vpant/thesis-p11pant/tree/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/searchtwitter "twitterdataminer/searchtwitter"). 

Το πακέτο ```database```, όπως προδίδει και η ονομασία του, είναι υπεύθυνο για την αποθήκευση των tweets χρησιμοποιώντας την [Data access object](https://en.wikipedia.org/wiki/Data_access_object "DAO Wikipedia") design patern για να αποξενωθεί η λογική αποθήκευσης των δεδομένων από την υπόλοιπη εφαρμογή. Οι κύριες λειτουργίες γίνονται στην κλάση [StatusDAO.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/database/StatusDAO.java "twitterdataminer/StatusDAO") όπου σε συνεργασία με τις υπόλοιπες κλάσεις του πακέτου εδραιώνουν σύνδεση στην βάση δεδομένων και ύστερα εκτελούν ερωτήματα για την αποθήκευση και την ανάκτηση των δεδομένων από αυτήν.

Το πακέτο ```http``` είναι υπεύθυνο για το χτίσιμο του ```http request``` που θα στείλουμε στο Twitter όπως και για να λάβει την απάντηση και να κρίνει εάν είναι "καλή" ή όχι (```NOT_FOUND```, ```BAD_REQUEST``` κ.α. δείτε [HttpResponseCode.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/http/HttpResponseCode.java "Resonse Codes")). Το ```http request``` που χρειαζόμαστε δημιουργείται και στέλνεται στην κλάση [HttpRequest.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/http/HttpRequest.java "twitterdataminer/http/HttpRequest.java") η οποία λαμβάνει την απάντηση και την αποθηκεύει σαν στιγμιότυπο της κλάσεις [HttpResponse.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/http/HttpResponse.java "Data object for Http Response") το οποίο αναθέτει σε τοπικές μεταβλητές τα απαραίτητα δεδομένα και προσφέρει και μια βοηθητική μέθοδο για την μετατροπή του ```HttpResponse``` σε ```JSONObject```.

Τέλος το πακέτο ```searchtwitter``` περιέχει το αντικείμενο [TwitterSearch.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/searchtwitter/TwitterSearch.java "Twitter search object") στο οποίο ορίζονται όλες οι παράμετροι όπως τα keywords που θα γίνουν search, το URL του API κ.α. και αρχικοποιεί την διαδικασία σύνδεσης με το Search API. Αφού μας επιστρέψει μια έγκυρη απάντηση το Twitter την περνάμε στο αντικείμενο [SearchResult](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/searchtwitter/SearchResult.java "Search result") το οποίο κάνει προσπέλαση τα αποτελέσματα τις απάντησης και αποθηκεύει σε μια λίστα από στιγμιότυπα του αντικειμένου [Status.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/searchtwitter/Status.java) το οποίο είναι μία κλάση δεδομένων που κάθε ένα στιγμιότυπο της αντιπροσωπεύει ένα Tweet.


Για καλύτερη πληροφόρηση για την διάρκεια ζωής της εφαρμογής γίνονται [logging](https://en.wikipedia.org/wiki/Log_file "Log file wikipedia") σημαντικές πληροφορίες, σφάλματα και διάφορα δεδομένα για πιο εύκολο [Debuging](https://en.wikipedia.org/wiki/Debugging "Debugging wikipedia"). Επιπλέον έχει δημιουργηθεί το αντικείμενο [TwitterException.java](https://github.com/vpant/thesis-p11pant/blob/master/TwitterDataMiner/src/main/java/org/twittercity/twitterdataminer/TwitterException.java), το οποίο κληρονομεί το αντικείμενο [Exception](https://docs.oracle.com/javase/7/docs/api/java/lang/Exception.html "Exception Javadoc") της Java, και είναι αρμόδιο για το [exception handling](https://en.wikipedia.org/wiki/Exception_handling "Exception handling wikipedia") που χρειάζεται η εφαρμογή μας. Παρακάτω παρατίθεται ένα διάγραμμα ροής του προγράμματος.
![data mining diagram](https://user-images.githubusercontent.com/11424758/36937090-c4cf916a-1f16-11e8-8bdd-1d9efea218c2.png)


##### TO-DOs

* Οι παράμετροι αναζήτησης να γίνονται αποθήκευση σε εξωτερικό μέσο αποθήκευσης (βάση δεδομένων, αρχείο) για δυνατότητα τροποποίησης από διαφορετική εφαρμογή ή διεπαφή.
* Συλλογή και αποθήκευση περισσότερων πληροφοριών όπως π.χ geolocation.
* Πειραματισμός με τα φίλτρα μέχρι να έχουμε ικανοποιητικές απαντήσεις από το Twitter.

###### Προτάσεις για βελτίωση (πιθανόν δεν θα υλοποιηθούν)
* Δημιουργία admin console για ευκολότερη διαχείριση της εφαρμογής (twitter credentials, keywords που θα χρησιμοποιηθούν κ.α.) 


### TwitterCityMod
##### Δόμηση Minecraft Mod

Το mod που θα γραφτεί για το minecraft θα έχει ως τελικό στόχο την δημιουργίας της πόλης. Παρακάτω δίνεται επιγραμματικά η δομή που θα έχει ο κώδικας για τις κύριες εργασίες που θα χρειάζεται να εκτελούνται:

1. Πρόσβαση, διάβασμα και δυνατότητα επεξεργασίας της βάσης δεδομένων που έχουν αποθηκευτεί τα tweets.
2. Αποθήκευση δεδομένων τοπικά για κάθε κόσμο και πρόσβαση σε αυτά.
3. Σύνολο αντικειμένων που θα χτίζουν τα blocks στην σωστή τους ακολουθία αποθηκεύοντας τα απαραίτητα [metadata](https://en.wikipedia.org/wiki/Metadata "Metadata wikipedia") (π.χ. Tweet ID που σχετίζεται το block)
4. [User Interface](https://en.wikipedia.org/wiki/User_interface "User interface wikipedia") που θα ανοίγει στον χρήστη όταν κάνει δεξί κλικ σε ένα tweet block και θα εμφανίζει το κείμενο του tweet και άλλες λεπτομέρειες όπως όνομα του χρήστη που το έγραψε, πότε δημιουργήθηκε κ.α.

Τα παραπάνω σημεία αφορούν την πιο κύρια δομή του mod τα οποία αυτά με την σειρά τους θα αποτελούνται από ένα μεγάλο πλήθος αντικειμένων και μεθόδων για την επίτευξη του αποτελέσματος που δηλώνουν, αλλά η ανάλυσή τους θα γίνει σε μελλοντικό παραδοτέο.

##### Κώδικας Minecraft Mod
Το αποθετήριο της εργασίας μου αποτελείτε από δύο κλαδιά (branches), το [master](https://github.com/vpant/thesis-p11pant) και το [development](https://github.com/vpant/thesis-p11pant/tree/development). Και τα δύο έχουν ίδια δομή και το master θα περιέχει κώδικα ο οποίος έχει υποβληθεί στα απαραίτητα tests και είναι σταθερός (stable), ενώ στο development κλαδί (branch) θα βρίσκεται ο κώδικας που αναπτύσσεται, κατά την διάρκεια της εργασίας. 

Ο κώδικας για το minecraft mod βρίσκεται στον φάκελο [TwitterCityMod](https://github.com/vpant/thesis-p11pant/tree/development/TwitterCityMod/src/main) όπου περιέχει δύο φακέλους, [java/org/twittercity/twittercitymod](https://github.com/vpant/thesis-p11pant/tree/development/TwitterCityMod/src/main/java/org/twittercity/twittercitymod) και [resources](https://github.com/vpant/thesis-p11pant/tree/development/TwitterCityMod/src/main/resources). Ο πρώτος περιέχει το κώδικα του mod ενώ ο δεύτερος όλα τα media που χρειάζεται το mod όπως textures, εικόνες κ.ο.κ. 

Σε αυτή την χρονική στιγμή το master κλαδί (branch) περιέχει κώδικα για την εύκολη δημιουργία blocks και items.


# Παραδοτέο 4
# Παραδοτέο 5
# Παραδοτέο 6
# Παραδοτέο 7
# Παραδοτέο 8
# Τελικό παραδοτέο

## Χρονοδιάγραμμα πτυχιακής εργασίας

- [x] Δήλωση θέματος και αρχικό πλάνο, εώς 15 Νοεμβρίου.
- [x] Εύρεση αναγκαίων εργαλείων, εφαρμογή τους και μία αρχική χρήση. Δημιουργίας λίστας από τις λειτουργίες που θα ήταν επιθυμητό να απαρτίζεται το πρόγραμμα.  (15 Δεκεμβρίου) 
- [x] Σχεδιασμός και υλοποίηση του script που θα κάνει data mining. Στόχοι: σωστοί δόμηση βάσης δεδομένων και εύκολη παραμετροποίηση των search queries.   (15 Ιανουαρίου)
- [ ] Υλοποίηση Rest API σε περίπτωση που αποφασιστεί ότι θα ωφελήσει την εφαρμογή. Σχεδιασμός δόμησης για το Minecraft Mod. Υλοποίηση του mod.(15 Φεβρουαρίου)
- [ ] Συνέχεια υλοποίησης (πιθανόν το mod να έχει αρκετές λειτουργίες επομένως απαιτεί χρόνο). Αποφασίζουμε τι ακριβώς extra θα υλοποιηθεί (π.χ έναν search function για την πλοήγηση στην πόλη ανάλογα με τον tweets θα πρόσφερε αρκετά στο user experience) και αν θα μας φτάσει ο χρόνος. Στη διάρκεια αυτού του μήνα απαιτείται και βελτιστοποίηση των δεδομένων που γίνονται mine. (15 Μαρτίου)
- [ ] Ολοκλήρωση κώδικα και δοκιμές τους και ξεκινάμε να χτίζουμε το dataset μας που θα απαρτίζεται στην τελική μας παρουσίαση. (15 Απριλίου)
- [ ] Συγγραφή του γραπτού μέρους της πτυχιακής εργασίας. (15 Απριλίου)
- [ ] Τελικό προσχέδιο αναφοράς και παρουσίασης για σχολιασμό (20%) - έως 15 Μαΐου.
- [ ] Τελική αναφορά και παρουσίαση (30%) - 1η Ιουνίου.


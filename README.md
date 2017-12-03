# Πτυχιακή Εργασία
**Όνομα Φοιτητή:** Βασίλειος Παντελής

**Α.Μ.:** Π2011003

**Θέμα πτυχιακής:** Οπτικοποίηση Δεδομένων στο βιντεοπαιχνίδι Minecraft

**Domain:** twittercity.org

## Τίτλος & περιγραφή πτυχιακής

### Συλλογή και αποθήκευση δεδομένων από το κοινωνικό δίκτυο Twitter και οπτικοποίηση στον εικονικό κόσμο του βιντεοπαιχνιδιού Minecraft


Η παρούσα πτυχιακή εργασία θα ασχοληθεί με την εξόρυξη δεδομένων από το κοινωνικό δίκτυο Twitter, με την σωστή διαχείριση αυτών (φιλτράρισμα, δόμηση και αποθήκευση)  και την δημιουργία Minecraft mod (συγγραφή κώδικα που θα τροποποιεί, θα προσθέτει ή/και θα αφαιρεί  λειτουργίες του παιχνιδιού). Το όνομα του minecraft mod θα είναι Twitter City και το logo στην ιστοσελίδα της πτυχιακής θα έχει σχέση με αυτήν την ονομασία.

Για την καλή οργάνωση και την εύκολη διαχείριση του πρακτικού μέρους της πτυχιακής θα χωριστεί σε τρία κομμάτια: πρόγραμμα εξόρυξης και διαχείρισης δεδομένων, [Minecraft Mod](https://minecraft.gamepedia.com/Mods/Forge) (οπτικοποίηση των δεδομένων) και ιστοσελίδα πτυχιακής.

Το πρόγραμμα για την εξόρυξη των δεδομένων από το Twitter θα χρησιμοποιεί το [Search API](https://developer.twitter.com/en/docs/tweets/search/overview/basic-search) αναζητώντας tweets με βάση κάποια keywords όπως «χτίζω σπίτι, πόλη κ.α.» (τα παραδείγματα είναι ενδεικτικά και σίγουρα θα τροποποιηθούν κατά την διάρκεια της βελτιστοποίησης ποιότητας και σαφήνειας τον εξορυγμένων tweets). Τα tweets θα αποθηκεύονται σε βάση δεδομένων που θα είναι προσβάσιμη (πιθανόν μέσω ενός Rest API που θα υλοποιηθεί) για να την διαχειριστούν και τα υπόλοιπα κομμάτια της εργασίας.

Συνεχίζοντας, το mod που θα δημιουργηθεί θα έχει σαν κύρια ευθύνη την δημιουργία της πόλης στον κόσμο του Minecraft. Δηλαδή, για κάθε tweet που έχει αποθηκεύσει το πρόγραμμα εξόρυξης δεδομένων από το Twitter ένα [block](http://minecraft.wikia.com/wiki/Blocks) θα χτίζετε στον κόσμο του Minecraft, το οποίο θα "κρατάει" και τις πληροφορίες του Tweet (κείμενο, συγγραφέας, ημερομηνία). Συνδυάζοντας τα blocks θα χτίζονται κατασκευές που θα δημιουργούν μια πόλη. Το mod θα σχεδιαστεί έτσι ώστε να δουλεύει ανεξάρτητα εάν είναι εγκατεστημένο σε Minecraft [Server](https://minecraft.gamepedia.com/Server) ή [Client](https://minecraft.gamepedia.com/Minecraft_launcher). Αυτό σημαίνει ότι θα εξαρτάται μόνο από την βάση δεδομένων που θα είναι αποθηκευμένες όλες οι απαραίτητες πληροφορίες για την σωστή λειτουργία του προγράμματος. Στόχος είναι η πόλη να γίνει μέρος του παιχνιδιού και να μην γίνει μεγάλη αλλαγή στην λογική του.

Η ιστοσελίδα της πτυχιακής θα αντιπροσωπεύει την παρουσίαση του mod και των λειτουργιών του και θα απαρτίζεται από χρήσιμο περιεχόμενο όπως είναι documentation, πληροφορίες για το Rest API και σύνδεσμο στο αποθετήριο στο github που θα περιέχει των κώδικα της πτυχιακής.

Τέλος, η πτυχιακή εργασία θα απαρτίζεται από το γραπτό κομμάτι που θα περιγράφει αναλυτικά κάθε τομέα της.

## Χρονοδιάγραμμα πτυχιακής εργασίας

- [x] Δήλωση θέματος και αρχικό πλάνο, εώς 15 Νοεμβρίου.
- [ ] Εύρεση αναγκαίων εργαλείων, εφαρμογή τους και μία αρχική χρήση. Δημιουργίας λίστας από τις λειτουργίες που θα ήταν επιθυμητό να απαρτίζεται το πρόγραμμα.  (15 Δεκεμβρίου) 
- [ ] Σχεδιασμός και υλοποίηση του script που θα κάνει data mining. Στόχοι: σωστοί δόμηση βάσης δεδομένων και εύκολη παραμετροποίηση των search queries.   (15 Ιανουαρίου)
- [ ] Υλοποίηση Rest API σε περίπτωση που αποφασιστεί ότι θα ωφελήσει την εφαρμογή. Σχεδιασμός δόμησης για το Minecraft Mod. Υλοποίηση του mod.(15 Φεβρουαρίου)
- [ ] Συνέχεια υλοποίησης (πιθανόν το mod να έχει αρκετές λειτουργίες επομένως απαιτεί χρόνο). Αποφασίζουμε τι ακριβώς extra θα υλοποιηθεί (π.χ έναν search function για την πλοήγηση στην πόλη ανάλογα με τον tweets θα πρόσφερε αρκετά στο user experience) και αν θα μας φτάσει ο χρόνος. Στη διάρκεια αυτού του μήνα απαιτείται και βελτιστοποίηση των δεδομένων που γίνονται mine. (15 Μαρτίου)
- [ ] Ολοκλήρωση κώδικα και δοκιμές τους και ξεκινάμε να χτίζουμε το dataset μας που θα απαρτίζεται στην τελική μας παρουσίαση. (15 Απριλίου)
- [ ] Συγγραφή του γραπτού μέρους της πτυχιακής εργασίας. (15 Απριλίου)
- [ ] Τελικό προσχέδιο αναφοράς και παρουσίασης για σχολιασμό (20%) - έως 15 Μαΐου.
- [ ] Τελική αναφορά και παρουσίαση (30%) - 1η Ιουνίου.

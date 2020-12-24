package cls.development.letschat;

public class DataRepository {
    public FirebaseClient firebaseClient;
    public static String ID;
    public static DataRepository dataRepository;
    public synchronized static DataRepository getInstance(){
        if(dataRepository == null)
            dataRepository= new DataRepository();
        return dataRepository;
    }

    public DataRepository() {
        firebaseClient = FirebaseClient.getInstance();
    }

    public void getFirebaseUid(){
        return FirebaseClient.getInstance().getUid();
    }
}

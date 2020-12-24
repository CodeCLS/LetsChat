package cls.development.letschat;

public class FirebaseClient {
    public static DataRepository dataRepository;
    public synchronized static DataRepository getInstance(){
        if(dataRepository == null)
            dataRepository= new DataRepository();
        return dataRepository;
    }
}

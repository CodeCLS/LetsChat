package cls.development.letschat.Interfaces;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface LoginNumberCallback {
    public void openDialogPhoneVerification(PhoneAuthProvider.ForceResendingToken token, String verificationId);
    public void successfullyVerified(PhoneAuthCredential credential);

}

package cls.development.letschat;

import com.google.firebase.auth.PhoneAuthProvider;

public interface LoginNumberCallback {
    public void openDialogPhoneVerification(PhoneAuthProvider.ForceResendingToken token, String verificationId);
    public void successfullyVerified(PhoneAuthProvider.ForceResendingToken token, String verificationId);

}

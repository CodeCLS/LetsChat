package cls.development.letschat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import cls.development.letschat.CustomViews.HeaderView;
import cls.development.letschat.Fragments.AllChatsFragment;
import cls.development.letschat.Fragments.LoginFragment;
import cls.development.letschat.FrontendManagement.ViewModel;
import cls.development.letschat.FrontendManagement.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private ViewModel viewModel;
    Uri deepLink = null;
    public HeaderView headerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        activityStartUp();
        headerWork();



    }

    private void headerWork() {
        viewModel.getInstaObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                headerView.setTextInsta(s);
            }
        });
    }

    private void initViews() {
        headerView = findViewById(R.id.header_view_main);
    }


    private void transitionToFragment(Fragment fragment) {
        if(fragment instanceof LoginFragment)
            headerView.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
    private void deepLinkWork() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            viewModel.setDeepLink(pendingDynamicLinkData.getLink());
                            viewModel.createChatFromDeepLink();


                        }



                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }




    @Override
    protected void onStart() {
        super.onStart();


    }

    private void activityStartUp() {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        viewModel = new ViewModelProvider(this,viewModelFactory).get(ViewModel.class);
        deepLinkWork();

        try {
            viewModel.initViewModelInActivity(this);

            AllChatsFragment allChatsFragment = new AllChatsFragment();
            transitionToFragment(allChatsFragment);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "activityStartUp: " + e);

            LoginFragment loginFragment = new LoginFragment();
            transitionToFragment(loginFragment);
        }




    }

    public void setHeaderVisibility(int visibility){
        headerView.setVisibility(visibility);
    }



}
package cls.development.letschat.FrontendManagement;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModel.class)) {
            return (T) new ViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


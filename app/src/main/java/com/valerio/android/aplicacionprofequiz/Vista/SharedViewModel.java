package com.valerio.android.aplicacionprofequiz.Vista;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> selectedName = new MutableLiveData<>();

    // Set the selected name
    public void selectName(String name) {
        selectedName.setValue(name);
    }

    // Get the selected name
    public LiveData<String> getSelectedName() {
        return selectedName;
    }
}

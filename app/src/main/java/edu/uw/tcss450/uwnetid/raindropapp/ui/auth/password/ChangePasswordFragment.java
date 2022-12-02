package edu.uw.tcss450.uwnetid.raindropapp.ui.auth.password;

import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdUpperCase;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.uwnetid.raindropapp.ui.auth.register.RegisterFragmentDirections;
import edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;

    private ChangePasswordViewModel mPasswordModel;


    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editNewPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonChange.setOnClickListener(this::attemptChange);
        mPasswordModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptChange(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.editEmail.setError("Please enter a valid Email address."));
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editNewPassword2.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.editNewPassword1.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editNewPassword1.setError("Passwords must match."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editNewPassword1.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editNewPassword1.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {
        mPasswordModel.connect(
                binding.editEmail.getText().toString(),
                binding.editPassword.getText().toString(),
                binding.editNewPassword1.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToLoginFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();

        directions.setEmail(binding.editEmail.getText().toString());
        directions.setPassword(binding.editNewPassword1.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                //navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}

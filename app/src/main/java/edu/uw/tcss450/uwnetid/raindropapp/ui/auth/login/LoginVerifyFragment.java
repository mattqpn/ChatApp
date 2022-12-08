package edu.uw.tcss450.uwnetid.raindropapp.ui.auth.login;

import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator.checkPwdUpperCase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.uwnetid.raindropapp.ui.auth.password.ChangePasswordViewModel;
import edu.uw.tcss450.uwnetid.raindropapp.ui.auth.register.RegisterFragmentDirections;
import edu.uw.tcss450.uwnetid.raindropapp.utils.PasswordValidator;

/**
 * Verification for email.
 */
public class LoginVerifyFragment extends Fragment {


}
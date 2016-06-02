package com.redhat.iot;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.redhat.iot.domain.Department;

import java.util.Collections;

/**
 * The home screen.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private MainActivity iotMain;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick( final View btn ) {
        final String deptId = btn.getTag().toString();

        // save selected dept IDs in preference
        final SharedPreferences.Editor editor = IotApp.getPrefs().edit();
        editor.putStringSet( IotConstants.Prefs.PROMO_DEPT_IDS, Collections.singleton( deptId ) );
        editor.apply();

        this.iotMain.showScreen( MainActivity.PROMOTIOHS_SCREEN_INDEX );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        this.iotMain = ( MainActivity )getActivity();
        final View view = inflater.inflate( R.layout.home, container, false );

        // create department buttons
        final Department[] departments = DataProvider.get().getDepartments();
        final TableLayout table = ( TableLayout )view.findViewById( R.id.homeDeptTable );
        TableRow row = null;
        int i = 0;
        int childIndex = 0;

        for ( final Department dept : departments ) {
            if ( i % 2 == 0 ) {
                childIndex = 0;
                row = new TableRow( this.iotMain );
                final TableLayout.LayoutParams params = new TableLayout.LayoutParams( TableLayout.LayoutParams.WRAP_CONTENT,
                                                                                      0,
                                                                                      1.0f );
                row.setLayoutParams( params );
                table.addView( row );
            }

            {// dept button
                inflater.inflate( R.layout.home_dept_button, row, true ); // adds to row
                final Button btn = ( Button )row.getChildAt( childIndex++ );
                btn.setBackgroundColor( DataProvider.get().getDepartmentColor( dept ) );
                btn.setText( dept.getName() );
                btn.setOnClickListener( this );
                btn.setTag( dept.getId() );

                final ViewOutlineProvider provider = new ViewOutlineProvider() {

                    @Override
                    public void getOutline( final View view,
                                            final Outline outline ) {
                        outline.setRoundRect( 10, 10, btn.getWidth() - 10, btn.getHeight() - 10, 30.0f );
                    }
                };
                btn.setOutlineProvider( provider );
                btn.setClipToOutline( true );
            }

            ++i;
        }

        return view;
    }

}

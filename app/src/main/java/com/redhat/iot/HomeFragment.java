package com.redhat.iot;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.redhat.iot.domain.Department;

/**
 * The home screen.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick( final View btn ) {
        Toast.makeText( getActivity(), " Department " + ( ( Button )btn ).getText(), Toast.LENGTH_SHORT ).show();
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final Activity activity = getActivity();
        final View view = inflater.inflate( R.layout.home, container, false );

        // create department buttons
        final Typeface cursive = Typeface.create( "cursive", Typeface.BOLD );
        final Department[] departments = DataProvider.get().getDepartments();
        final TableLayout table = ( TableLayout )view.findViewById( R.id.homeDeptTable );
        TableRow row = null;
        int i = 0;

        for ( final Department dept : departments ) {
            if ( i % 2 == 0 ) {
                row = new TableRow( activity );
                final TableLayout.LayoutParams params = new TableLayout.LayoutParams( TableLayout.LayoutParams.WRAP_CONTENT,
                                                                                      0,
                                                                                      1.0f );
                row.setLayoutParams( params );
                table.addView( row );
            }

            {// dept button
                final Button btn = new Button( activity );
                final TableRow.LayoutParams params = new TableRow.LayoutParams( 0,
                                                                                TableRow.LayoutParams.MATCH_PARENT,
                                                                                1.0f );
                btn.setLayoutParams( params );
                btn.setBackgroundColor( DataProvider.get().getDepartmentColor( dept ) );
                btn.setText( dept.getName() );
                btn.setTextSize( 30 );
                btn.setTextColor( getActivity().getResources().getColor( R.color.colorPrimaryDark, null ) );
                btn.setTypeface( cursive );
                btn.setOnClickListener( this );

                final ViewOutlineProvider provider = new ViewOutlineProvider() {

                    @Override
                    public void getOutline( final View view,
                                            final Outline outline ) {
                        outline.setRoundRect( 10, 10, btn.getWidth() - 10, btn.getHeight() - 10, 30.0f );
                    }
                };
                btn.setOutlineProvider( provider );
                btn.setClipToOutline( true );

                btn.setElevation( 10.0f );
                row.addView( btn );
            }

            ++i;
        }

        return view;
    }

}

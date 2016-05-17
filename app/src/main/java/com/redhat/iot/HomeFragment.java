package com.redhat.iot;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.redhat.iot.domain.Department;

/**
 * The home screen.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
//
//    private static final int[] COLORS = new int[]{
//        Color.parseColor( "#E6EE7C" ),
//        Color.parseColor( "#ADEE7C" ),
//        Color.parseColor( "#E7A146" ),
//        Color.parseColor( "#EE7CAD" ),
//        Color.parseColor( "#7CE6EE" ),
//        Color.parseColor( "#BD7CEE" ),
//        Color.parseColor( "#7CADEE" ) };

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
        final Department[] departments = DataProvider.get().getDepartmentsFromJson();
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
                btn.setBackgroundResource( R.drawable.rounded_corner );
//                btn.setBackgroundColor(COLORS[i]);
                btn.setText( dept.getName() );
                btn.setTextSize( 30 );
                btn.setTypeface( cursive );
                btn.setPadding( 30, 30, 30, 30 );
                btn.setOnClickListener( this );
                row.addView( btn );
            }

            ++i;
        }

        return view;
    }

}

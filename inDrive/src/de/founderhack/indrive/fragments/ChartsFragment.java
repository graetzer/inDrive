package de.founderhack.indrive.fragments;

import java.util.ArrayList;
import java.util.List;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

import de.founderhack.indrive.DataBuffer;
import de.founderhack.indrive.R;
import de.founderhack.indrive.dsa.DiagnosticValue;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChartsFragment extends Fragment {
	
	private Handler mTimer;
	private Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			updateCharts();
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_charts, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mTimer = new Handler();
		updateCharts();
	}
	
	private void updateCharts() {
		if(getView() == null) return;
		
		long timestamp = System.currentTimeMillis()-1000*60*60;
		
		LineGraph speedG = (LineGraph)getView().findViewById(R.id.graphSpeed);
		LineGraph fuelG = (LineGraph)getView().findViewById(R.id.graphFuel);
		LineGraph tempG = (LineGraph)getView().findViewById(R.id.graphTemp);
		
		List<DiagnosticValue> vals = DataBuffer.getInstance(getActivity()).speed;
		updateChart(speedG, condense(vals, timestamp, 50),"#FFBB33");
		
		vals = DataBuffer.getInstance(getActivity()).fuelReserve;
		updateChart(fuelG, condense(vals, timestamp, 50),"#DDEE55");
		
		vals = DataBuffer.getInstance(getActivity()).temp;
		updateChart(tempG, condense(vals, timestamp, 50),"#00EE55");
		
		//mTimer.postDelayed(updateRunnable, 2000);
	}
	
	private void updateChart (LineGraph graph, List<DiagnosticValue> list, String color) {
		if (graph != null && list != null && list.size() > 0) {
			graph.removeAllLines();
			
			Line l = new Line();
			l.setColor(Color.parseColor(color));
			
			for (int i = 0; i < list.size(); i++) {
				DiagnosticValue val = list.get(i);
				
				LinePoint p = new LinePoint();
				p.setX(i);
				p.setY(val.getValue());
				l.addPoint(p);
			}
			graph.addLine(l);
			graph.setLineToFill(0);
		}
	}
	
	@Override
	public void onPause() {
		mTimer.removeCallbacks(updateRunnable);
		super.onDestroy();
	}
	
	private List<DiagnosticValue> condense(List<DiagnosticValue> vals, long timestamp, int maxResults) {
		maxResults = Math.min(maxResults, vals.size());
		if (maxResults == 0) return null;
		
		ArrayList<DiagnosticValue> results = new ArrayList<DiagnosticValue>(maxResults);
		int start = findNearestIndex(vals, timestamp);
		int steps = Math.min( (vals.size() - start)/maxResults, 1);
		for (int i = vals.size()-1; i > start; i -= steps) {
			results.add(vals.get(i));
		}
		
		return results;
	}
	
	private int findNearestIndex(List<DiagnosticValue> vals, long timestamp) {
		long minDiff = Math.abs(vals.get(0).getTime() - timestamp) ;
		int index = 0;
		for (int i = 1; i < vals.size(); i++) {
			long diff = Math.abs(vals.get(i).getTime() - timestamp);
			if (diff < minDiff) {
				index = i;
			}
		}
		return index;
	}
}

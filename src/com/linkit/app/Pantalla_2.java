package com.linkit.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import api.LibE105Linkit;

@SuppressWarnings("rawtypes")
public class Pantalla_2 extends Activity implements OnClickListener
{

	String 		msj,msj2,msj3 ;
	Button		BotonIr,BotonFecha,BotonTamper,BotonBateria;
	ArrayList	Objarray1,Objarray2,Objarray3;
	TextView 	textoFecha,textoTamper,textoBateria;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pantalla_2);
		
		BotonIr 		= (Button) findViewById(R.id.bIrPantalla1);
		BotonFecha 		= (Button) findViewById(R.id.bFecha);
		BotonTamper 	= (Button) findViewById(R.id.bTamper);
		BotonBateria 	= (Button) findViewById(R.id.bBateria);
		
		BotonIr.setOnClickListener(this);
		BotonFecha.setOnClickListener(this);
		BotonTamper.setOnClickListener(this);
		BotonBateria.setOnClickListener(this);

        
		textoFecha  	= (TextView) findViewById(R.id.tFecha);	
		textoTamper  	= (TextView) findViewById(R.id.tTamper); 
		textoBateria  	= (TextView) findViewById(R.id.tBateria);
        
	}
	
	@Override
	public void onClick(View v) 
	{ 
		resetearTextView();
		
		if(v.getId()==findViewById(R.id.bIrPantalla1).getId())
		{
			finish();
			Intent myIntent = new Intent(this, Pantalla_1.class);
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.bFecha).getId())
		{
			LibE105Linkit.vdGetDateE105(this);
			WaitResponse(1);
		}
		
		if(v.getId()==findViewById(R.id.bTamper).getId())
		{
			LibE105Linkit.vdGetTamperStatusE105(this);
			WaitResponse(3);
		}
		
		if(v.getId()==findViewById(R.id.bBateria).getId())
		{
			LibE105Linkit.vdGetBatteryInfoE105(this);
			WaitResponse(2);
		}
	}
	
	public void imprimir(final int flag)
	{
			runOnUiThread(new Runnable() 
			{
			    @Override
				public void run()
			    {
			    	if(flag==1)
			    	{
			    		textoFecha.setText(msj);
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","Fecha: "					+Objarray1.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
			    	}
			    	if(flag==2)
			    	{
			    		textoBateria.setText(msj);
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","Nivel Batería: "			+Objarray1.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==3)
			    	{
			    		textoTamper.setText(msj);
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","Estado Tamper: "			+Objarray1.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    }
			});		
	}
	
	/** metodo que implementa el hilo que espera la respuesta proveniente del E105**/
	public void WaitResponse(int flag)
	{
		if(flag==1)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistDate();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistDate();
	        			msj			=	Objarray1.get(2).toString();
	        		}imprimir(1);             
	        	}
	        };hilo.start();
		}
		
		if(flag==2)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistBattery();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistBattery();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(2);             
	        	}
	        };hilo.start();
		}
		
		if(flag==3)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistTamper();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistTamper();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(3);             
	        	}
	        };hilo.start();
		}
	}
	
	
	/** metodo que inicializa lso textviews**/	
	public void resetearTextView()
	{
		textoFecha.setText("******");
		textoTamper.setText("******");
		textoBateria.setText("******");
	}
	
}

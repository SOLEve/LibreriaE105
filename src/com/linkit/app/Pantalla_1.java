package com.linkit.app;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import api.LibE105Linkit;

@SuppressWarnings("rawtypes")
public class Pantalla_1 extends Activity implements OnClickListener
{	
	String 		msj,msj2,msj3 ;
	Button 		botonSerial,botonMsr,botonPIN,botonE0B,botonE03,botonAbort,botonEndEmv,BotonIr;
	TextView 	textoSerial,textoMsr,textoPIN,textoE0B,textoE03,textoAbort,textoEndEmv;
	ArrayList	Objarray1,Objarray2,Objarray3;
	Intent 		myIntent;
	
	@Override   
	protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pantala_1);

        /** Creacion de los botones y texview**/
        botonSerial = (Button) findViewById(R.id.b4);    
        botonMsr = (Button) findViewById(R.id.b5);	
        botonPIN = (Button) findViewById(R.id.b6);    
        botonE0B = (Button) findViewById(R.id.b7);	
        botonE03 = (Button) findViewById(R.id.b8);    
        botonAbort = (Button) findViewById(R.id.b9);	
        botonEndEmv = (Button) findViewById(R.id.b10);
        BotonIr = (Button) findViewById(R.id.bIrPantalla2);
        
        botonSerial.setOnClickListener(this);	
        botonMsr.setOnClickListener(this);    
        botonPIN.setOnClickListener(this);	
        botonE0B.setOnClickListener(this);    
        botonE03.setOnClickListener(this);    
        botonAbort.setOnClickListener(this);	
        botonEndEmv.setOnClickListener(this);
        BotonIr.setOnClickListener(this);
        
        textoSerial  = (TextView) findViewById(R.id.t4);	
        textoMsr  = (TextView) findViewById(R.id.t5); 
        textoPIN  = (TextView) findViewById(R.id.t6);	
        textoE0B  = (TextView) findViewById(R.id.t7); 
        textoE03  = (TextView) findViewById(R.id.t8);	
        textoAbort  = (TextView) findViewById(R.id.t9); 
        textoEndEmv  = (TextView) findViewById(R.id.t10);
    }    
   
	@Override
	public void onClick(View v) 
	{        
		resetearTextView();
		if(v.getId()==findViewById(R.id.bIrPantalla2).getId())
		{
			finish();
			Intent myIntent = new Intent(this, Pantalla_2.class);
	        this.startActivity(myIntent);
		}
		if(v.getId()==findViewById(R.id.b4).getId())
		{
			LibE105Linkit.vdGetInformationPinPad(this);
			WaitResponse(4);
		}
		if(v.getId()==findViewById(R.id.b5).getId())
		{
			LibE105Linkit.vdGetMsrE105(this,"0A","Deslice Tarjeta ahora...");
			WaitResponse(5);
		}		
		if(v.getId()==findViewById(R.id.b6).getId())
		{
			LibE105Linkit.vdGetPedE105(
					this,
					"Ingrese PIN: ",				//texto1 PIN
					"Re-Ingrese PIN: ",				//texto2 PIN
		    "Re-Ingrese PIN 'Ultima Oportunidad': ",//texto3 PIN
					"PIN INCORRECTO",				//titulo PIN incorrecto
					"Resta 2 Oportunidades",		//texto1 PIN incorrecto
					"Resta 1 Oportunidad",			//texto2 PIN incorrecto
					"TARJETA BLOQUEADA",			//titulo PIN bloqueado
					"Contacte a su banco",			//texto  tarjeta bloqueada
					"10");							//timeoutPIN
			WaitResponse(6);
		}
		if(v.getId()==findViewById(R.id.b7).getId())
		{
			LibE105Linkit.vdStartEMVTransactionE105(
					this,
					"0A",							//timeoutCardDetectLoop
					"Ingrese o Deslice Tarjeta...",	//textoCardDetectLoop
					"0A",							//timeoutMsrRead
					"Deslice Tarjeta...",			//textoMsrRead
					"FF",							//timeoutSmartCard
					"Inserte Chip: ",				//textoSmartCardEnable
					"10",							//timeoutSeleccApp
					"Seleccione Aplicacion",		//textoSeleccApp
					"Cancelar",						//textoCancelarSeleccApp
					"10",							//timeoutPIN					
					"Ingrese PIN: ",				//texto1 PIN
					"Re-Ingrese PIN: ",				//texto2 PIN
		    "Re-Ingrese PIN 'Ultima Oportunidad': ",//texto3 PIN
					"PIN INCORRECTO",				//titulo PIN incorrecto
					"Resta 2 Oportunidades",		//texto1 PIN incorrecto
					"Resta 1 Oportunidad",			//texto2 PIN incorrecto
					"TARJETA BLOQUEADA",			//titulo PIN bloqueado
					"Contacte a su banco",			//texto  tarjeta bloqueada					
					"FALLBACK!",					//tituloFallback
					"Presione Ok para continuar...",//textoFallback
					"000000900000",					//montoTransaccion monto = 90.00
					"000000001000",					//montoTransaccionAdicional monto = 10.50
					"09");							//TransactionType
												
			WaitResponse(7);
		}
		if(v.getId()==findViewById(R.id.b8).getId())
		{
			LibE105Linkit.vdContinueEMVOnlineE105(this,
					"3030",
					"91081122334455667788",
					"71169F180410000000860D84180000087801EB453CCBCC24",
					"722E9F180440000000862504DA8E00200000000000000000420141035E031F020000000000000000BEF5D629856C4DD5");
			WaitResponse(8);
		}
		if(v.getId()==findViewById(R.id.b9).getId())
		{
			LibE105Linkit.vdAbort(this);
			WaitResponse(9);
		}
		if(v.getId()==findViewById(R.id.b10).getId())
		{
			LibE105Linkit.vdEndEmv(this);
			WaitResponse(10);
		}
	}

	public void imprimir(final int flag)
	{
			runOnUiThread(new Runnable() 
			{
			    @Override
				public void run()
			    {
			    	if(flag==4)
			    	{
			    		textoSerial.setText(Objarray1.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","N° Serial: "				+Objarray1.get(2).toString());
				    	Log.i("Principal","Versión S.O: "			+Objarray1.get(3).toString());
				    	Log.i("Principal","Versión Libreria: "		+Objarray1.get(4).toString());
				    	Log.i("Principal","Versión Kernel: "		+Objarray1.get(5).toString());
				    	Log.i("Principal","Marca Terminal: "		+Objarray1.get(6).toString());
				    	Log.i("Principal","Modelo Terminal: "		+Objarray1.get(7).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==5)
			    	{
			    		textoMsr.setText(msj);
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","OBFUSCATED PAN: "		+Objarray1.get(2).toString());
				    	Log.i("Principal","CARDHOLDER NAME: "		+Objarray1.get(3).toString());
				    	Log.i("Principal","ENCRYPTED TRACK 2 DATA: "	+Objarray1.get(4).toString());
				    	Log.i("Principal","KSN FOR ENCRYPTED TRACK 2: "	+Objarray1.get(5).toString());
				    	Log.i("Principal","SERVICE CODE: "				+Objarray1.get(6).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==6)
			    	{
			    		textoPIN.setText(msj);
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","PIN encriptado: "		+Objarray1.get(2).toString());
				    	Log.i("Principal","KSN + counter: "			+Objarray1.get(3).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
			    	}
			    	if(flag==7)
			    	{
			    		textoE0B.setText("Comando vdStartEMVTransactionE105 finalizado");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "+Objarray1.get(1).toString());
				    	Log.i("Principal","RESPUESTA E0B: "				+Objarray1.get(2).toString());
				    	Log.i("Principal","OBFUSCATED PAN: "			+Objarray1.get(3).toString());
				    	Log.i("Principal","CARDHOLDER NAME: "			+Objarray1.get(4).toString());
				    	Log.i("Principal","ENCRYPTED TRACK 2 DATA: "	+Objarray1.get(5).toString());
				    	Log.i("Principal","KSN FOR ENCRYPTED TRACK 2: "	+Objarray1.get(6).toString());
				    	Log.i("Principal","SERVICE CODE: "				+Objarray1.get(7).toString());
				    	Log.i("Principal","PIN BLOCK ENCRIPTADO: "		+Objarray1.get(8).toString());
				    	Log.i("Principal","KSN PIN BLOCK: "				+Objarray1.get(9).toString());
				    	Log.i("Principal","Modo de Extraccion de Datos: "+Objarray1.get(11).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==8)
			    	{
			    		textoE03.setText("Comando vdContinueEMVOnlineE105 finalizado");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray1.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray1.get(1).toString());
				    	Log.i("Principal","Decision 2do Certificado: "	+Objarray1.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==9)
			    	{
			    		textoAbort.setText("Operacion Finalizada");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray2.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray2.get(1).toString());
				    	Log.i("Principal"," "						+Objarray2.get(2).toString());
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    }
			    	if(flag==10)
			    	{
			    		textoEndEmv.setText("Operacion Finalizada");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","*****************************************************");
				    	Log.i("Principal","Estatus de Procesamiento: "		+Objarray3.get(0).toString());
				    	Log.i("Principal","Mensaje del Estatus de Procesamiento: : "	+Objarray3.get(1).toString());
				    	Log.i("Principal"," "						+Objarray3.get(2).toString());
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
		if(flag==4)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistInformationPinPad();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistInformationPinPad();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(4);             
	        	}
	        };hilo.start();
		}
		if(flag==5)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistMSR();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistMSR();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(5);             
	        	}
	        };hilo.start();
		}
		if(flag==6)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistPIN();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			Log.i("","hilo");
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistPIN();
	        			msj			=	Objarray1.get(2).toString();
	        		}imprimir(6);             
	        	}
	        };hilo.start();
		}
		if(flag==7)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistStartEMVTransactionE105();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistStartEMVTransactionE105();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(7);             
	        	}
	        };hilo.start();
		}
		if(flag==8)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray1	=	LibE105Linkit.alGetArraylistStartEMVOnlineE105();
	        		msj			=	Objarray1.get(2).toString();
	        		
	        		while(msj.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray1	=	LibE105Linkit.alGetArraylistStartEMVOnlineE105();
		        		msj			=	Objarray1.get(2).toString();
	        		}imprimir(8);             
	        	}
	        };hilo.start();
		}
		if(flag==9)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray2	=	LibE105Linkit.alGetArraylistAbort();
	        		msj2		=	Objarray2.get(2).toString();
	        		
	        		while(msj2.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray2	=	LibE105Linkit.alGetArraylistAbort();
		        		msj2		=	Objarray2.get(2).toString();
	        		}imprimir(9);             
	        	}
	        };hilo.start();
		}
		if(flag==10)
		{
			Thread hilo = new Thread()
	        {
	        	@Override
				public void run()
	        	{   
	        		Objarray3	=	LibE105Linkit.alGetArraylistEndEmv();
	        		msj3		=	Objarray3.get(2).toString();
	        		
	        		while(msj3.equalsIgnoreCase(""))
	        		{
	        			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	        			Objarray3	=	LibE105Linkit.alGetArraylistEndEmv();
		        		msj3		=	Objarray3.get(2).toString();
	        		}imprimir(10);             
	        	}
	        };hilo.start();
		}


	}

	/** metodo que inicializa lso textviews**/	
	public void resetearTextView()
	{
		textoSerial.setText("******");
		textoMsr.setText("******");
		textoPIN.setText("******");
		textoE0B.setText("******");
		textoE03.setText("******");
		textoAbort.setText("******");	
		textoEndEmv.setText("******");
	}

		
}

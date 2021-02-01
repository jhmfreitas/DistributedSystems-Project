package com.forkexec.hub.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.ws.Response;

import com.forkexec.hub.domain.exceptions.PointsNotFoundException;
import com.forkexec.hub.ws.BalanceView;
import com.forkexec.pts.ws.GetPointsBalanceResponse;
import com.forkexec.pts.ws.SetPointsBalanceResponse;
import com.forkexec.pts.ws.cli.PointsClient;
import com.forkexec.pts.ws.cli.PointsClientException;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINamingException;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDIRecord;

/**
 * HubManager
 *
 * A frontEnd for replica management
 *
 */
public class HubManager {
	
	/**
	 * Replica template name
	 */
	private String templateName = "T22_Points";
	
	private int nReplicas = 3;

	private int quorum = this.nReplicas/2 +1;

	private String uddiURL = null;
	
	public void setUddiURL(String uddiURL) {
		this.uddiURL = uddiURL;
	}
	
	public String getUddiURL() {
		return uddiURL;
	}
	
	// Singleton -------------------------------------------------------------

	/** Private constructor prevents instantiation from other classes. */
	private HubManager() {
		// Initialization of default values
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final HubManager INSTANCE = new HubManager();
	}

	public static synchronized HubManager getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public Collection<String> getPointsClients() {
		Collection<UDDIRecord> records = null;
		Collection<String> clients = new ArrayList<String>();
		try {
			UDDINaming uddi = new UDDINaming(uddiURL);
			records = uddi.listRecords(templateName + "%");
			for (UDDIRecord u : records) {
				clients.add(u.getOrgName());
			}
		} catch (UDDINamingException e) {
			System.out.println("UDDINamingException: " + e.toString());
		}
		return clients;
	}
	
	public PointsClient getPointsClient(String clientId){
		Collection<String> clients = this.getPointsClients();
		String uddiUrl = HubManager.getInstance().getUddiURL();
		for (String s : clients) {
			if(clientId.equals(s)) {
				try {
					return new PointsClient(uddiUrl, s);
				} catch (PointsClientException e) {
					continue;
				}
			}
			
		}
		return null;
		
	}
	
	public synchronized BalanceView read(String userEmail) throws PointsNotFoundException {
		int receivedResponses = 0;
		List<Response<GetPointsBalanceResponse>> responses = new ArrayList<Response<GetPointsBalanceResponse>>();
		BalanceView balanceView = new BalanceView();
		balanceView.setTag(-1);
		balanceView.setValue(-1);
		
		for(int i = 1; i <= this.nReplicas; i++) {
			PointsClient pointsClient = getPointsClient(templateName + i);
			if (pointsClient == null) {
				continue;
			}
			responses.add(pointsClient.getPointsBalanceAsync(userEmail));
		}

		while(receivedResponses < this.quorum) {
			for(Response<GetPointsBalanceResponse> response : responses) {
				if(response.isDone()) {
					try {
						if(response.get().getBalanceView() != null) {
							if(response.get().getBalanceView().getTag() > balanceView.getTag()) {
								balanceView.setTag(response.get().getBalanceView().getTag());
								balanceView.setValue(response.get().getBalanceView().getValue());
								System.out.println("Tag:" + balanceView.getTag());
								System.out.println("Value:" + balanceView.getValue());
							}
						}
					}catch (ExecutionException e) {
						responses.remove(response);
						continue;
					} catch (InterruptedException e) {
						continue;
					}

					responses.remove(response);
					receivedResponses++;
					break;
				}

			}
		}
		return balanceView;
	}



	public synchronized void write(String userEmail, int value, int flag) throws PointsNotFoundException {
		//flag = 0 -> add
		//flag = 1 -> spend
		int receivedResponses = 0;
		BalanceView balanceView = read(userEmail);
		int newTag = balanceView.getTag() + 1;
		List<Response<SetPointsBalanceResponse>> responses = new ArrayList<Response<SetPointsBalanceResponse>>();
		
		if(flag == 0) {
			for(int i = 1; i <= this.nReplicas; i++) {
				PointsClient pointsClient = getPointsClient(templateName + i);
				if (pointsClient == null)
					continue;
				responses.add(pointsClient.addPointsAsync(userEmail, value, newTag));
			}
		}
		
		else {
			for(int i = 1; i <= this.nReplicas; i++) {
				PointsClient pointsClient = getPointsClient(templateName + i);
				if (pointsClient == null)
					continue;
				responses.add(pointsClient.spendPointsAsync(userEmail, value, newTag));
			}
		}
		
		while(receivedResponses < this.quorum) {
			for(Response<SetPointsBalanceResponse> response : responses) {
				if(response.isDone()) {
					receivedResponses++;
					responses.remove(response);
					break;
				}

			}
		}
		
	}
	
}
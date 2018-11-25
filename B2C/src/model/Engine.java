package model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import Orders.CustomerType;
import Orders.ItemType;
import Orders.ItemsType;
import Orders.ObjectFactory;
import Orders.OrderType;


public class Engine {
	private final String poPath = "/home/user/git/FoodsRUs/B2C/Orders/";
	private static Engine instance = null;
	private static ObjectFactory factory = new ObjectFactory();
	private Engine() {

	}

	/**
	 * @Description creates and delivers a singleton instance
	 * @return returns the singleton Engine object
	 */
	public synchronized static Engine getInstance() {
		if (instance == null)
			instance = new Engine();
		return instance;
	}

	/**
	 * @Description It takes in a search term and searches the database through the
	 *              ItemDAO
	 * @param searchTerm
	 * @return a List<ItemBean> containing all items that match the search term
	 * @throws Exception
	 */
	public List<ItemBean> doItem(String searchTerm, String flag) throws Exception {
		/*
		 * split the search term by using regex loopthrough the array and pass a single
		 * search term to the DAO get the list from the DAO and add the contents to the
		 * total list return the total list
		 */
		/*
		 * if flag is not null, then the search bar is used with multiple search words
		 */
		String bySearchTerm = "bySearchTerm";
		if (bySearchTerm.equals(flag)) {
			String split[] = searchTerm.split("[\\s+]|[,+]");
			ArrayList<String> terms = new ArrayList<>();
			for (String term : split) {
				if (!term.isEmpty()) {
					terms.add(term);
				}
			}
			return ItemDAO.retrieve(terms);
		} else {
			/* if flag is null, then it is being searched by a specific identifier */
			/* parse the catID to int and pass it to the DAO */
			int catid = -1;
			try {
				catid = Integer.parseInt(searchTerm);
			} catch (Exception e) {
				throw new Exception("internal problem catID is not parsed to integer");
			}

			return ItemDAO.retrieve(catid);
		}

	}

	public void createOrders() throws DatatypeConfigurationException, JAXBException, IOException {
		
		CustomerType customer = factory.createCustomerType();
		
		ItemType item = factory.createItemType();
		ItemsType items = factory.createItemsType();
		OrderType order = factory.createOrderType();
		BigDecimal totalPrice;
		customer.setName("Amin");
		customer.setAccount("new account");
		item.setName("chicken");
		item.setNumber("34");
		item.setPrice(new BigDecimal(12.3));
		totalPrice = item.getPrice(); 
		item.setQuantity(BigInteger.valueOf( new Integer(3).intValue()));
		List<ItemType> itemsList = items.getItem();
		itemsList.add(item);
		item.setName("cheese");
		item.setNumber("35");
		item.setPrice(new BigDecimal(12));
		item.setQuantity(BigInteger.valueOf( new Integer(12).intValue()));
		itemsList.add(item);
		totalPrice.add(item.getPrice());
		order.setCustomer(customer);
		order.setItems(items);
		order.setTotal(totalPrice);
		order.setShipping(new BigDecimal(12));
		order.setHST(order.getGrandTotal().multiply(new BigDecimal(0.13)));
		order.setGrandTotal(totalPrice.add(order.getHST().add(order.getShipping())));
		order.setId(BigInteger.valueOf(new Integer(321).intValue()));
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		order.setSubmitted(date2);
		JAXBContext jc = JAXBContext.newInstance(order.getClass());
		Marshaller marsh = jc.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marsh.marshal(order, new File(poPath+customer.getName()+"_"+order.getId().toString()+".xml"));

		
	}

	public List<CategoryBean> doCategory(String prefix) throws Exception {
		return CategoryDAO.retrieve(prefix);
	}

}
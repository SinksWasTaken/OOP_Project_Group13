package com.group13.Controllers.Cashier;

//WHATS LEFT: ADd discount functionsality, separate adding the ticket to the db from displaying it

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.group13.DatabaseConnection;
import com.group13.Models.Model;
import com.group13.Models.TicketModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CashierPurchaseTicketController 
{
    @FXML
    private TextField purchaseField;

    @FXML
    private Label priceLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label totalLabel;

    @FXML
    private TableView<TicketModel> purchaseTable;
    @FXML
    private TableColumn<TicketModel, String> ticketIDColumn;
    @FXML
    private TableColumn<TicketModel, String> seatsColumn;
    @FXML
    private TableColumn<TicketModel, String> productsColumn;
    
    @FXML
    private Button nextStageButton;
    @FXML
    private Button discountButton;
    @FXML
    private Button cancelButton;


    private final ObservableList<TicketModel> ticketsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatStr"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("productStr"));

        
        System.err.println("\n\n\n "+"P:" + Model.getInstance().getTicketModel().productStr);
        System.err.println("\n\n\n "+"S:" + Model.getInstance().getTicketModel().seatStr);
        

        purchaseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nextStageButton.setDisable(true);
        //discountButton.setDisable(true);
        if(purchaseField.getText().trim()!="")
        {
            Integer age = Integer.parseInt(purchaseField.getText().trim());
            if(age>=1 && age<=120)
            {
                discountButton.setDisable(false);
            }

        }

        discountLabel.setText(discountLabel.getText()+0+"%");

        setTicketStrings();
        onDiscount();



        
        discountButton.setOnAction(event -> onDiscount());
        nextStageButton.setOnAction(event -> onPurchase());

    }

    private void setTicketStrings()
    {
        TicketModel ticket = Model.getInstance().getTicketModel();
        try 
        {
            String productsStr=null;
            for(int i=0;i<ticket.productIDs.size();i++)
            {
                productsStr+=ticket.productIDs.get(i);
                if(i<ticket.productIDs.size()-1)
                {
                    productsStr+=" - ";
                }
            }

            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String seatsStr=null;
            String seatIDStr = null;

            for(int i=0; i<ticket.allSeats.size();i++)
            {
                
                seatIDStr+=ticket.allSeats.get(i);
                String selectSeatQuery = "SELECT * FROM seats where seat_id="+ticket.allSeats.get(i);
                ResultSet seatsRs = statement.executeQuery(selectSeatQuery);

                seatsRs.next();

                seatsStr+= seatsRs.getInt("col") + seatsRs.getString("row");

                if(i<ticket.allSeats.size()-1)
                {
                    seatsStr+=" - ";
                    seatIDStr+=" - ";
                }
            }

            ticket.seatStr=seatsStr;
            ticket.productStr=productsStr;
            ticket.seatsIDStr=seatIDStr;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    private void onDiscount() 
    {
        
        try
        {
            TicketModel ticket = Model.getInstance().getTicketModel();
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            String priceTableQuery = "select * from pricetaxdiscount";
            ResultSet rs = statement.executeQuery(priceTableQuery);
            rs.next();
            int price = rs.getInt("movieTicketPrice");
            int ticketTaxRate = rs.getInt("ticketTaxRate");
            int discount = rs.getInt("discountRate");
            int productTaxRate = rs.getInt("productTaxRate");


            for(int i=0;i<ticket.productIDs.size()-1;i++)//Sorts tickets' prroductIDs bubble sort
            {
                for(int j=i+1;j<ticket.productIDs.size();j++)
                {
                    if(Integer.parseInt(ticket.productIDs.get(i))> Integer.parseInt(ticket.productIDs.get(j)))
                    {
                        String temp = ticket.productIDs.get(i);
                        ticket.productIDs.set(i,ticket.productIDs.get(j));
                        ticket.productIDs.set(j,temp);
                    }
                }
            }


            String productsQuery = "select * from products";
            
            rs = statement.executeQuery(productsQuery);
            int counter=0;
            while(rs.next())
            {
                if(rs.getInt("productID")==Integer.parseInt(ticket.productIDs.get(counter)))
                {
                    ticket.totalProductPrice+=rs.getInt("productID");
                    counter++;
                }
            }

            ticket.totalTicketPrice=ticket.allSeats.size()*price;
            ticket.totalTicketPrice*=ticketTaxRate/100;
            ticket.totalProductPrice*=productTaxRate/100;


            priceLabel.setText(priceLabel.getText()+ "Tickets: "+ticket.totalTicketPrice + " - Products: "+ ticket.totalProductPrice);

            if(purchaseField.getText().trim()!="")
            {
                Integer age = Integer.parseInt(purchaseField.getText().trim());
                if(age>=1 && age<=18)
                {
                    discountLabel.setText(discountLabel.getText()+discount+"%");
                }

            }
            


            totalLabel.setText(totalLabel.getText()+ (ticket.totalTicketPrice+ticket.totalProductPrice - (ticket.totalTicketPrice+ticket.totalProductPrice)*discount/100));
        
            Model.getInstance().setTicketModel(ticket);
            ticketsList.add(ticket);
            purchaseTable.setItems(ticketsList);

            nextStageButton.setDisable(false);
            
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }

    @FXML
    private void onPurchase() 
    {
        purchaseTicket(Model.getInstance().getTicketModel());
    }
    
    
    public boolean purchaseTicket(TicketModel ticket)
    {
        
        try
        {
            String selectAllTicketsQuery = "SELECT * FROM tickets";

            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(selectAllTicketsQuery);

            rs=statement.executeQuery(selectAllTicketsQuery);
            rs.moveToInsertRow();

            rs.updateString("customerName", ticket.customerName+"- "+ticket.ticketID);
            rs.updateInt("customerAge", ticket.customerAge);
            rs.updateInt("sessionID",ticket.getSessionID());
            rs.updateString("seatIDs", ticket.seatsIDStr);
            rs.updateInt("movieID", ticket.movieID);
            rs.updateString("productIDs", ticket.productStr);
            rs.updateInt("numberOfTickets", ticket.numberOfTickets);
            rs.updateDouble("discountRate", ticket.discountRate);
            rs.updateDouble("totalTicketPrice", ticket.totalTicketPrice);
            rs.updateDouble("totalProductPrice", ticket.totalProductPrice);
            rs.updateDate("purchaseDate", ticket.purchaseDate);
            rs.insertRow();

            System.out.println("Purchased Ticket Successfully");

            String selectSeatQuery = "SELECT * FROM seats Where sessionID="+ticket.sessionID;
            rs=statement.executeQuery(selectSeatQuery);


            for(int i=0;i<ticket.allSeats.size()-1;i++)//Sorts tickets bubble sort
            {
                for(int j=i+1;j<ticket.allSeats.size();j++)
                {
                    if(ticket.allSeats.get(i)>ticket.allSeats.get(j))
                    {
                        int temp = ticket.allSeats.get(i);
                        ticket.allSeats.set(i,ticket.allSeats.get(j));
                        ticket.allSeats.set(j,temp);
                    }
                }
            }

            int counter=0;
            while(rs.next())
            {
                if(rs.getInt("seat_id")==ticket.allSeats.get(counter))
                {
                    rs.updateBoolean("sold", true);
                    counter++;
                }
            }
            
            System.out.println("Seat Sold Successfully");

            ticketsList.add(ticket);

            return true;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println("Couldn't Buy Ticket");
            return false;
        }
    }
}

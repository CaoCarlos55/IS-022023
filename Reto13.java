import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;

class Reto13{
    public static void main(String[] args){

        ICentro principal = new ICentro();
    }
}

class ICentro extends JFrame implements ActionListener{
    String desc; 
    int ct;
    float mu;
    String dt, nf, ci;

    //Etiquetas
    JLabel datas = new JLabel("Ingrese los datos del equipo:");

    JLabel description = new JLabel("Descripcion: ");
    JTextField descriptionText = new JTextField(30);

    JLabel quantity = new JLabel("Cantidad:");
    JTextField quantityText = new JTextField(10);

    JLabel cost = new JLabel("Costo Unitario (Bs.):");
    JTextField costText = new JTextField(10);

    JLabel date = new JLabel("Fecha de adquisicion:");

    JLabel dateF = new JLabel("dd/mm/aaaa");
    JTextField dateText = new JTextField(10);

    JLabel invoice = new JLabel("Nro. de Factura:");
    JTextField invoiceText = new JTextField(20);

    JLabel cedula = new JLabel("C.I. del Responsable del equipo:");
    JTextField cedulaText = new JTextField(15);

    JButton data = new JButton("Registrar data");
    JButton report = new JButton("Generar reporte");
    JButton exit = new JButton("Salir"); 

    ICentro(){
        super("Registro y Control de Equipos en Centros de Investigacion");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Campo Datas
        add(datas);
        datas.setBounds(10, 10, 500,20);

        //Campo Descripcion
        add(description);
        add(descriptionText);
        description.setBounds(10, 50, 500,20);
        descriptionText.setBounds(90, 50, 480,20);

        //Campo Cantidad
        add(quantity);
        add(quantityText);
        quantity.setBounds(10, 90, 480,20);
        quantityText.setBounds(90, 90, 100,20);

        //Campo Costo
        add(cost);
        add(costText);
        cost.setBounds(300, 90, 480,20);
        costText.setBounds(420, 90, 150,20);

        //Campo Fecha
        add(date);
        add(dateF);
        add(dateText);
        date.setBounds(10, 130, 480,20);
        dateF.setBounds(10, 150, 100,20);
        dateText.setBounds(140, 130, 100,20);

        //Campo Factura
        add(invoice);
        add(invoiceText);
        invoice.setBounds(320, 130, 480,20);
        invoiceText.setBounds(420, 130, 150,20);

        //Campo Cedula
        add(cedula);
        add(cedulaText);
        cedula.setBounds(10, 190, 480,20);
        cedulaText.setBounds(200, 190, 100,20);
        
        //Boton Data
        add(data);
        data.addActionListener(this);
        data.setBounds(185, 330, 120,20);

        //Boton Reporte
        add(report);
        report.addActionListener(this);
        report.setBounds(310, 330, 135,20);

        //Boton Salir 
        add(exit);
        exit.addActionListener(this);
        exit.setBounds(450, 330, 125, 20);

        setVisible(true);
    }

    @Override 
    public void actionPerformed(ActionEvent a){
        JButton button = (JButton) a.getSource();
    
        if(button == data){
            desc = descriptionText.getText();
            ct = Integer.parseInt(quantityText.getText());
            mu = Float.parseFloat(costText.getText());
            dt = dateText.getText();
            nf = invoiceText.getText(); 
            ci = cedulaText.getText();

            try{
                Path filePath = Paths.get("Reto13.txt");
                OutputStream output = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE, APPEND));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

                writer.write(desc + "#" + ct + "#" + mu + "#" + dt + "#" + nf + "#" + ci +";\n");
                writer.close();
                output.close();
            }
            catch(Exception e){
                System.out.println("ERROR: " + e);
            }
        }
        else if(button == report){
            setVisible(false);
            IReporte report = new IReporte();
        }
        else if(button == exit){
            dispose();
        }
    }
}

    class IReporte extends JFrame implements ActionListener{

    JLabel typeReport = new JLabel("Tipo reporte:");

    JCheckBox individual = new JCheckBox("Individual");

    JCheckBox general = new JCheckBox("General");

    JLabel cedulaReport = new JLabel("C.I. del Responsable de equipos:");
    JTextField cedulaReportText = new JTextField(15);

    JButton total = new JButton("Totalizar");

    JLabel totalization = new JLabel("Totalizacion:");
    JLabel numbersTeams = new JLabel("___equipos");
    JLabel bolivars = new JLabel("___Bs.");

    JButton continuar = new JButton("Continuar");
    JTextArea text = new JTextArea();
    JScrollPane table = new JScrollPane(text);

    IReporte(){
        super("Registro y Control de Equipos en Centros de Investigacion");
        setSize(600, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        //Campo cuadro
        add(table);

        //Campo Reporte
        add(typeReport);
        typeReport.setBounds(10, 10, 200, 20);

        add(individual);
        individual.addActionListener(this);
        individual.setBounds(100, 10, 100, 20);


        add(general);
        general.addActionListener(this);
        general.setBounds(210, 10, 200, 20);

        //Campo Cedula
        add(cedulaReport);
        add(cedulaReportText);

        //Campo Totalizacion
        add(total);
        add(totalization);
        total.addActionListener(new accionBotones());
        totalization.setBounds(10, 260, 200, 20);

        //Campo Equipo
        add(numbersTeams);
        numbersTeams.setBounds(30, 280, 100, 20);
       
        //Campo Bolivares
        add(bolivars);
        bolivars.setBounds(30, 300, 200, 20);

        //Campo Continuar
        add(continuar);        
        continuar.addActionListener(new accionBotones());
        continuar.setBounds(450, 300, 100, 20);
       
        setVisible(true);
    }

    @Override 
    public void actionPerformed(ActionEvent a){
        JCheckBox box = (JCheckBox) a.getSource();
        int quantityTeams = 0;
        float totalCost = 0f;

        if(box.isSelected()){
            if(box == individual){
                general.setSelected(false);
                cedulaReport.setBounds(10, 50, 600, 20);
                cedulaReportText.setBounds(200, 50, 100, 20);
                total.setBounds(305, 50, 100, 20);
                table.setBounds(0, 0, 0, 0);                
            }
            else if(box == general){
                individual.setSelected(false);
                cedulaReport.setBounds(0, 0, 0, 0);
                cedulaReportText.setBounds(0, 0, 0, 0);
                total.setBounds(0, 0, 0, 0);
                text.setText("C.I. Responsable                                            Cantidad equipos                                              Monto Total (Bs.)\n"); 
                table.setBounds(10, 80, 565, 100);
                LinkedList<Employee> employees = new LinkedList<Employee>();
                String lecture;
                boolean isInList = false;
                try{
                    Path filePath = Paths.get("Reto13.txt");
                    InputStream input = new BufferedInputStream(Files.newInputStream(filePath, CREATE));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    lecture = reader.readLine();                    
                    while(lecture != null){
                        String[] lectureArray = lecture.split("#");
                        String[] cedulaArray = lectureArray[5].split(";");
                        for(int i = 0; i < employees.size(); i++){
                            if(cedulaArray[0].equals(employees.get(i).cedula)){
                                isInList = true;
                                employees.get(i).quantityTeams += Integer.parseInt(lectureArray[1]);
                                employees.get(i).totalCost += Float.parseFloat(lectureArray[2]) * Integer.parseInt(lectureArray[1]);
                            }
                        }
                        
                        lecture = reader.readLine();
                        if(isInList){
                            isInList = false;
                            continue;
                        }
                        else{
                            employees.add(new Employee(cedulaArray[0], Integer.parseInt(lectureArray[1]), Integer.parseInt(lectureArray[1]) * Float.parseFloat(lectureArray[2])));
                        }
                    }

                    for(int i = 0; i < employees.size(); i++){
                        text.append(employees.get(i).cedula + "                                                        " + employees.get(i).quantityTeams + "                                                                            " + employees.get(i).totalCost + "\n");
                    }

                    for(int i = 0; i < employees.size(); i++){
                        quantityTeams+= employees.get(i).quantityTeams;
                        totalCost += employees.get(i).totalCost;
                    }
                    numbersTeams.setText(quantityTeams + " equipos");
                    bolivars.setText(totalCost + " Bs.");
                    input.close();
                    reader.close();
                }catch(Exception e){
                    System.out.println("ERROR:" + e);
                }
            }
        }
   }
   class accionBotones implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            JButton button = (JButton) a.getSource();
            int teams = 0;
            float money = 0f;
            if(button == total){
                String lecture;
                try{
                    Path filePath = Paths.get("Reto13.txt");
                    InputStream input = new BufferedInputStream(Files.newInputStream(filePath));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    lecture = reader.readLine();                    
                    while(lecture != null){
                        String[] lectureArray = lecture.split("#");
                        String[] cedulaArray = lectureArray[5].split(";");
                        if(cedulaReportText.getText().equals(cedulaArray[0])){
                            teams += Integer.parseInt(lectureArray[1]);
                            money += Float.parseFloat(lectureArray[2]) * Integer.parseInt(lectureArray[1]);
                        }
                        lecture = reader.readLine();
                    }
                    numbersTeams.setText(teams + " equipos");
                    bolivars.setText(money + " Bs.");
                    input.close();
                    reader.close();
                }catch(Exception e){
                    System.out.println("ERROR:" + e);
                }
            }
            else if(button == continuar){
                setVisible(false);
                ICentro principal = new ICentro();
            }        
        }
   }
}

class Employee{
    public String cedula;
    public int quantityTeams = 0;
    public float totalCost = 0; 

    Employee(String cedulaEmployee, int quantity, float cost){
        cedula = cedulaEmployee;
        quantityTeams += quantity;
        totalCost += cost;
    }
}
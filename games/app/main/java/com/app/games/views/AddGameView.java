package com.app.games.views; 

import com.app.games.model.Game;  
import com.app.games.service.GameService; 
import com.vaadin.flow.component.button.Button;  
import com.vaadin.flow.component.checkbox.Checkbox;  
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;  
import java.util.List;  

@Route("add-game")  
public class AddGameView extends VerticalLayout {  

    private final GameService gameService;  

    // Componentes de la UI  
    private TextField nombreField = new TextField();  
    private TextField tipoField = new TextField();  
    private TextField empresaDesarrolladoraField = new TextField();  
    private TextField plataformasField = new TextField();  
    private TextField cantidadJugadoresLocalField = new TextField();  
    private DatePicker fechaSalidaField = new DatePicker();  
    private Checkbox coOpLocalCheckbox = new Checkbox("Co-Op Local");  
    private Checkbox coOpOnlineCheckbox = new Checkbox("Co-Op Online"); 
    private TextField puntuacionField = new TextField(); 
    private TextField comentarioField = new TextField(); 
    private Button saveButton = new Button("Add Game");  
    private Button backButton = new Button("Go Back"); 
    
    private HeaderComponent header;

    public AddGameView(GameService gameService) {  
        this.gameService = gameService; 

        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull();

        header = new HeaderComponent();  
        
        // Solicitando que el título no sea clickeable y ocultando el perfil  
        header.setTitleClickable(true);  
        header.setProfileLinkVisible(true);  

        // Agregar el header a la vista  
        add(header);
        
        H3 title = new H3("Add Game");   
        title.getStyle().set("margin-left", "20px");   
        add(title);   

        // Cambiar HorizontalLayout por VerticalLayout para hacer que los elementos estén uno debajo del otro  
        VerticalLayout layout1 = new VerticalLayout(createRow("Name", nombreField),  
            createRow("Genre", tipoField), createRow("Developer", empresaDesarrolladoraField),  
            createRow("Platforms (separated by comma)", plataformasField), createRow("Quantity Local CO-OP", cantidadJugadoresLocalField),  
            createRow("Release Date", fechaSalidaField), createRow(coOpLocalCheckbox, coOpOnlineCheckbox), createRow("Review", puntuacionField),  
            createRow("Commentary", comentarioField));  
        layout1.getStyle().set("margin-left", "20px");   

        // Crear un layout para los botones y añadir margenes para separación  
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-left", "20px").set("margin-bottom", "20px");  

        // Añadir todos los layouts al componente principal  
        add(layout1, buttonLayout);   

        // Añadir listener para el botón de guardar  
        saveButton.addClickListener(e -> saveGame());  

        // Añadir listener para el botón de regresar  
        backButton.addClickListener(e -> navigateBack());  
    }  

    private HorizontalLayout createRow(String labelText, TextField textField) {  
        textField.setLabel(labelText);  
        return new HorizontalLayout(textField);  
    }  

    private HorizontalLayout createRow(String labelText, DatePicker datePicker) {  
        datePicker.setLabel(labelText);  
        return new HorizontalLayout(datePicker);  
    }  

    private HorizontalLayout createRow(Checkbox... checkboxes) {  
        HorizontalLayout layout = new HorizontalLayout();  
        for (Checkbox checkbox : checkboxes) {  
            layout.add(checkbox);  
        }  
        return layout;  
    }  

    private void saveGame() {  
        String userId = (String) VaadinSession.getCurrent().getAttribute("userId");
        Game game = new Game();  
        game.setNombre(nombreField.getValue());  
        game.setTipo(tipoField.getValue());  
        game.setEmpresaDesarrolladora(empresaDesarrolladoraField.getValue());  

        // Convertir la lista de plataformas de String a List<String>  
        String plataformasInput = plataformasField.getValue();  
        List<String> plataformasList = new ArrayList<>();  
        if (!plataformasInput.isEmpty()) {  
            for (String plataforma : plataformasInput.split(",")) {  
                plataformasList.add(plataforma.trim());  
            }  
        }  
        game.setPlataformas(plataformasList);  

        game.setCantidadJugadoresLocal(cantidadJugadoresLocalField.getValue());  

        // Obtener la fecha desde el DatePicker, si no hay valor se establece null  
        game.setFechaSalida(fechaSalidaField.getValue() != null ? fechaSalidaField.getValue().toString() : null);  
        
        game.setCoOpLocal(coOpLocalCheckbox.getValue());  
        game.setCoOpOnline(coOpOnlineCheckbox.getValue()); 
        
        String puntuacion = puntuacionField.getValue();
        String comentarios = comentarioField.getValue();

        // Llamar al servicio para crear el juego  
        Game createdGame = gameService.createGame(game, userId, puntuacion, comentarios);  
        
        // Notificar al usuario  
        Notification.show("Juego agregado: " + createdGame.getNombre());  

        // Limpiar los campos  
        clearFields();  

        getUI().ifPresent(ui -> ui.navigate("/"));
    }  

    private void clearFields() {  
        nombreField.clear();  
        tipoField.clear();  
        empresaDesarrolladoraField.clear();  
        plataformasField.clear();  
        cantidadJugadoresLocalField.clear();  
        fechaSalidaField.clear();  
        coOpLocalCheckbox.clear();  
        coOpOnlineCheckbox.clear();  
    }  

    private void navigateBack() {  
        // Redirige a la vista anterior, cambiar "main" con la ruta de tu vista principal  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  
}  
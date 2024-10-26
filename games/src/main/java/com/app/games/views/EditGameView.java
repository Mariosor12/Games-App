package com.app.games.views;  

import com.app.games.model.Game;  
import com.app.games.service.GameService;  
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.router.BeforeEnterEvent;  
import com.vaadin.flow.router.BeforeEnterObserver;  
import com.vaadin.flow.router.Route;  

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;  

@Route("edit-game/:gameId")  
public class EditGameView extends VerticalLayout implements BeforeEnterObserver {  

    private final GameService gameService;  
    private TextField nombreField;  
    private TextField tipoField;  
    private TextField empresaDesarrolladoraField;  
    private TextField plataformasField; // Puedes usar un TextField para entrada separada por comas  
    private TextField cantidadJugadoresLocalField;  
    private TextField fechaSalidaField;  
    private Checkbox coOpLocalCheckbox;  
    private Checkbox coOpOnlineCheckbox;   
    private String gameId;

    private HeaderComponent header;

    @Autowired  
    public EditGameView(GameService gameService) {  
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
        
        H3 title = new H3("Edit Game");
        title.getStyle().set("margin-left", "20px");   
        add(title);  

        nombreField = new TextField("Name");  
        tipoField = new TextField("Genre");  
        empresaDesarrolladoraField = new TextField("Developer");  
        plataformasField = new TextField("Platforms (separated by comma)");  
        cantidadJugadoresLocalField = new TextField("Quantity Local CO-OP");  
        fechaSalidaField = new TextField("Release Date");
        coOpLocalCheckbox = new Checkbox("Local Co-Op");  
        coOpOnlineCheckbox = new Checkbox("Online Co-Op");

        Button backButton = new Button("Go Back"); 
        backButton.getStyle().set("margin-left", "20px");  

        Button saveButton = new Button("Confirm Changes"); 
        saveButton.getStyle().set("margin-left", "20px"); 

        VerticalLayout layout1 = new VerticalLayout(createRow("Name", nombreField), createRow("Genre", tipoField), createRow("Developer", empresaDesarrolladoraField), createRow("Platforms (separated by comma)", plataformasField),
        createRow("Quantity Local CO-OP", cantidadJugadoresLocalField), createRow("Release Date", fechaSalidaField), createRow(coOpLocalCheckbox, coOpOnlineCheckbox));  
        layout1.getStyle().set("margin-left", "20px").set("width", "auto");  

        // Crear un layout para los botones utilizando VerticalLayout  
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-bottom", "20px");  

        // Añadir todos los layouts al componente principal  
        add(layout1, buttonLayout); 

        // Añadir listener para el botón de guardar  
        saveButton.addClickListener(e -> saveGame());  

        // Añadir listener para el botón de regresar  
        backButton.addClickListener(e -> navigateBack()); 
    }  

    private void navigateBack() {  
        // Redirige a la vista anterior, cambiar "main" con la ruta de tu vista principal  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  

    private HorizontalLayout createRow(String labelText, TextField textField) {  
        textField.setLabel(labelText);  
        return new HorizontalLayout(textField);  
    }  

    private HorizontalLayout createRow(Checkbox... checkboxes) {  
        HorizontalLayout layout = new HorizontalLayout();  
        for (Checkbox checkbox : checkboxes) {  
            layout.add(checkbox);  
        }  
        return layout;  
    }  

    @Override  
    public void beforeEnter(BeforeEnterEvent event) {   
        this.gameId = event.getRouteParameters().get("gameId").orElse("");  
        Optional<Game> optionalGame = gameService.getGameById(gameId);  
        optionalGame.ifPresent(game -> populateForm(game));  
    }  

    private void populateForm(Game game) {  
        nombreField.setValue(game.getNombre());  
        tipoField.setValue(game.getTipo());  
        empresaDesarrolladoraField.setValue(game.getEmpresaDesarrolladora());  
        plataformasField.setValue(String.join(", ", game.getPlataformas())); // Convertir a string  
        cantidadJugadoresLocalField.setValue(game.getCantidadJugadoresLocal());  
        fechaSalidaField.setValue(game.getFechaSalida());  
        coOpLocalCheckbox.setValue(game.isCoOpLocal());  
        coOpOnlineCheckbox.setValue(game.isCoOpOnline());  
    }  

    private void saveGame() {  
        Game game = new Game();  

        game.setNombre(nombreField.getValue());  
        game.setTipo(tipoField.getValue());  
        game.setEmpresaDesarrolladora(empresaDesarrolladoraField.getValue());  
        game.setPlataformas(Arrays.asList(plataformasField.getValue().split(",")));  
        game.setCantidadJugadoresLocal(cantidadJugadoresLocalField.getValue());  
        game.setFechaSalida(fechaSalidaField.getValue());  
        game.setCoOpLocal(coOpLocalCheckbox.getValue());  // Obtiene el valor del checkbox  
        game.setCoOpOnline(coOpOnlineCheckbox.getValue());
  
        gameService.updateGame(this.gameId, game);  
        
        Notification.show("Juego actualizado exitosamente!", 3000, Notification.Position.MIDDLE); 
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  
}
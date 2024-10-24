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

        nombreField = new TextField("Nombre");  
        tipoField = new TextField("Tipo");  
        empresaDesarrolladoraField = new TextField("Empresa Desarrolladora");  
        plataformasField = new TextField("Plataformas (separadas por comas)");  
        cantidadJugadoresLocalField = new TextField("Cantidad de Jugadores Locales");  
        fechaSalidaField = new TextField("Fecha de Salida");
        coOpLocalCheckbox = new Checkbox("Co-Op Local");  
        coOpOnlineCheckbox = new Checkbox("Co-Op Online");

        Button backButton = new Button("Regresar"); 
        backButton.getStyle().set("margin-left", "20px");  

        Button saveButton = new Button("Guardar cambios"); 
        saveButton.getStyle().set("margin-left", "20px"); 

        HorizontalLayout layout1 = new HorizontalLayout(createRow("Name", nombreField), createRow("Genre", tipoField));  
        layout1.getStyle().set("margin-left", "20px");  

        HorizontalLayout layout2 = new HorizontalLayout(createRow("Developer", empresaDesarrolladoraField), createRow("Platforms (separated by comma)", plataformasField));  
        layout2.getStyle().set("margin-left", "20px");  

        HorizontalLayout layout3 = new HorizontalLayout(createRow("Quantity Local CO-OP", cantidadJugadoresLocalField), createRow("Release Date", fechaSalidaField));  
        layout3.getStyle().set("margin-left", "20px");  

        HorizontalLayout layout4 = new HorizontalLayout(createRow(coOpLocalCheckbox, coOpOnlineCheckbox));  
        layout4.getStyle().set("margin-left", "20px");  

        // Crear un layout para los botones  
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-left", "20px").set("margin-bottom", "20px");  

        // Añadir todos los layouts al componente principal  
        add(layout1, layout2, layout3, layout4, buttonLayout); 

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
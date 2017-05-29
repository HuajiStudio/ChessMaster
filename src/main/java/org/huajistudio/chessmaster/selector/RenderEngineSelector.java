package org.huajistudio.chessmaster.selector;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.huajistudio.chessmaster.ChessMaster;
import org.huajistudio.chessmaster.api.render.Renderer;
import org.huajistudio.chessmaster.render.BGFXRenderer;
import org.huajistudio.chessmaster.util.i18n.I18n;
import ro.fortsoft.pf4j.PluginWrapper;

public class RenderEngineSelector extends Application {
	class RenderWrapper {
		private Renderer renderer;

		public RenderWrapper(Renderer renderer) {
			this.renderer = renderer;
		}

		@Override
		public String toString() {
			PluginWrapper plugin = ChessMaster.getPluginManager().whichPlugin(renderer.getClass());
			if (plugin == null)
				return I18n.format(I18n.CURRENT_LOCALE.get(), "chessmaster", "render." + renderer.getName());
			else
				return I18n.format(I18n.CURRENT_LOCALE.get(), plugin.getPluginId(), "render." + renderer.getName());
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setMaxSize(800, 600);
		pane.setPrefSize(800, 600);
		pane.setManaged(true);
		pane.setVgap(3);
		pane.setHgap(3);
		pane.addRow(1, new Label("Chess Master"));
		ComboBox<RenderWrapper> renderCombo = new ComboBox<>();
		renderCombo.getItems().addAll(new RenderWrapper(new BGFXRenderer()));
		ChessMaster.getPluginManager().getExtensions(Renderer.class).stream().map(RenderWrapper::new).forEach(renderCombo.getItems()::add);
		pane.addRow(2, new Label("Select a renderer:"), renderCombo);
		Button button = new Button("Start!");
		button.setOnAction(event -> {
			ChessMaster.getLogger().info("Using renderer: {} ({})", renderCombo.getSelectionModel().getSelectedItem().renderer.getName(), renderCombo.getSelectionModel().getSelectedItem().renderer.getClass().getName());
			renderCombo.getSelectionModel().getSelectedItem().renderer.render();
			primaryStage.close();
		});
		pane.addRow(3, button);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}
}

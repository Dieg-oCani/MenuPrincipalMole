package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Juego;

public class MenuPrincipal implements Screen

{
	private static final int ANCHO_MUNDO = 1280;
	private static final int ALTO_MUNDO = 800;
	private final Juego juego;
	//escena en la pantalla
	private Stage escena;

	//Textura para el fondo de pantalla
	private Texture texturaFondo;

	//Textura para el título
	private Texture texturaTitulo;


	//Texturas de los botones
	private Texture texturaBtnJugar;
	private Texture texturaBtnOpciones;
	private Texture texturaBtnAcercaDe;


	//Administra la carga de assets
	private final AssetManager assetManager = new AssetManager();

	//camara
	private OrthographicCamera camara;
	private Viewport vista;

	public MenuPrincipal(Juego juego) {
		this.juego = juego;
	}


	public void create () {
		//Se ejecuta al cargar la pantalla

		cargarTexturas();

		escena = new Stage();

		//Habilitar el manejo de eventos
		Gdx.input.setInputProcessor(escena);

		//calacular ancho y alto de la pantalla fisica
		//ahora lo debemos hacer con la camara virtual

		float ancho = ANCHO_MUNDO;//Gdx.graphics.getWidth();
		float alto = ALTO_MUNDO;//Gdx.graphics.getHeight();

		//fondo
		Image imgFondo = new Image(texturaFondo);
		//escalar
		float escalaX = ancho / imgFondo.getWidth();
		float escalaY = alto / imgFondo.getHeight();
		imgFondo.setScale(escalaX, escalaY);
		escena.addActor(imgFondo);

		//Agregar botones
		//Jugar

		TextureRegionDrawable trBtnJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
		ImageButton btnJugar = new ImageButton(trBtnJugar);
		btnJugar.setPosition(ancho / 2 - btnJugar.getWidth() / 2, 0.6f * alto);
		escena.addActor(btnJugar);

		//Opciones
		TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
		ImageButton btnOpciones = new ImageButton(trBtnOpciones);
		btnOpciones.setPosition(ancho / 2 - btnOpciones.getWidth() / 2, 0.4f * alto);
		escena.addActor(btnOpciones);

		//Acerca de
		TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
		ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
		btnAcercaDe.setPosition(ancho / 2 - btnAcercaDe.getWidth() / 2, 0.2f * alto);
		escena.addActor(btnAcercaDe);

		//Agregar titulo
		Image imgTitulo = new Image(texturaTitulo);
		imgTitulo.setPosition(ancho / 2 - imgTitulo.getWidth() / 2, 0.8f * alto);
		escena.addActor(imgTitulo);

		// Registrar listener para atender evento botón (monitorea a un objeto y avisa si se ha ejecuado un evento.

		btnJugar.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("clicked", "Tap sobre el botón de jugar");

				//Cambiar pantalla a juego
				juego.setScreen(new PantallaNivel(juego));


			}
		});

		btnOpciones.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("clicked","Se hizo tap sobre Opciones");

				//Cambiar pantalla a opciones
				juego.setScreen(new PantallaOpciones());

			}
		});

		btnAcercaDe.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y ){
				Gdx.app.log("clicked","Tap sobre el BOTON Acerca de...");

				//Cambiar pantalla
				juego.setScreen(new PantallaAcercaDe(juego));
			}
	});

	}

	private void cargarTexturas() {

		//assetManager carga las imagenes

		//Textura de fondo
		assetManager.load("fondo.jpg", Texture.class);
		//Textura de botones
		assetManager.load("jugar.png", Texture.class);
		assetManager.load("opciones.png",Texture.class);
		assetManager.load("acerca_de.png",Texture.class);
		assetManager.load("titulo.png", Texture.class );





		//se bloquea hasta cargar los recursos
		assetManager.finishLoading();

		//cuando termina leemos las texturas
		texturaFondo = assetManager.get("fondo.jpg");
		texturaBtnJugar = assetManager.get("jugar.png");
		texturaBtnOpciones = assetManager.get("opciones.png");
		texturaBtnAcercaDe = assetManager.get("acerca_de.png");
		texturaTitulo = assetManager.get("titulo.png");


	}


	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		escena.setViewport(vista);

		escena.draw();
	}

	@Override
	public void show() {

		//camara
		camara = new OrthographicCamera(ANCHO_MUNDO,ALTO_MUNDO);
		camara.position.set(ANCHO_MUNDO/2, ALTO_MUNDO/2,0);
		camara.update();
		vista = new FitViewport(ANCHO_MUNDO,ALTO_MUNDO,camara);

		//Equivale a create

		create();
	}

	@Override
	public void render(float delta) {
		//Equivale a render()
		render();
	}

	@Override
	public void resize(int width, int height) {
		vista.update(width,height);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		//Se ejevuta cuando se esconde la pantalla
		dispose();
	}

	@Override
	public void dispose () {
		//liberar la memoria utilizada por los recursos
		 texturaFondo.dispose();
		texturaFondo.dispose();
		texturaBtnAcercaDe.dispose();
		texturaTitulo.dispose();
		texturaBtnOpciones.dispose();
		texturaBtnJugar.dispose();
		escena.dispose();

	}
}

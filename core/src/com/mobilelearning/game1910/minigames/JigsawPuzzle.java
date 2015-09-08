package com.mobilelearning.game1910.minigames;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mobilelearning.game1910.screens.StandardScreen;
import com.mobilelearning.game1910.uiUtils.PolygonRegionDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: AFFonseca
 * Date: 02-03-2015
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class JigsawPuzzle extends Table{

    private DragAndDrop dragAndDrop;
    private Table puzzleTable;
    private JigsawPuzzleCallback callback;
    private Table pieceSpace;
    private int numberOfPiecesLeft;
    private Image [] scatteredPieces;
    private Image [] piecesInPlace;
    private DragAndDrop.Source [] sources;
    private DragAndDrop.Target [] targets;

    public JigsawPuzzle(Stage stage){
        super();
        this.center().top();
        this.setFillParent(true);

        dragAndDrop = new DragAndDrop();

        pieceSpace = new Table();
        pieceSpace.setTouchable(Touchable.enabled);
        dragAndDrop.addTarget(new JigsawPuzzleDragAndDropTarget(pieceSpace));

        puzzleTable = new Table();

        stage.addActor(this);

    }

    public void setImageAndPieces(Drawable originalImage, PolygonRegionDrawable puzzlePieces[]){
        numberOfPiecesLeft = puzzlePieces.length;
        piecesInPlace = new Image[puzzlePieces.length];
        scatteredPieces = new Image[puzzlePieces.length];
        sources = new DragAndDrop.Source[puzzlePieces.length];
        targets = new DragAndDrop.Target[puzzlePieces.length];

        add(puzzleTable).size(originalImage.getMinWidth(), originalImage.getMinHeight()).row();
        add(pieceSpace).size(pieceSpace.getWidth(), pieceSpace.getHeight());



        for(int i=0; i<puzzlePieces.length; i++){
            Image pieceImage = new Image(puzzlePieces[i]);
            piecesInPlace[i] = pieceImage;
            pieceImage.setPosition(puzzlePieces[i].getStartingX(), puzzlePieces[i].getStartingY());

            DragAndDrop.Target target = new JigsawPuzzleDragAndDropTarget(pieceImage);
            targets[i] = target;
            dragAndDrop.addTarget(target);
            pieceImage.getColor().a = 0.0f;
            puzzleTable.addActor(pieceImage);

            Image scatteredPiece = new Image(puzzlePieces[i]);
            scatteredPieces[i] = scatteredPiece;
            scatteredPiece.setPosition(
                    MathUtils.random(0.0f,  pieceSpace.getWidth() - scatteredPiece.getWidth()),
                    MathUtils.random(0.0f,  pieceSpace.getHeight() - scatteredPiece.getHeight()));
            pieceSpace.addActor(scatteredPiece);

            DragAndDrop.Source source = new JigsawPuzzleDragAndDropSource(scatteredPiece);
            sources[i] = source;
            dragAndDrop.addSource(source);
            this.addActor(scatteredPiece);
        }

    }

    public int getNumberOfPiecesLeft(){
        return numberOfPiecesLeft;
    }

    public void putRandomPieceInPlace(){
        int randomValue = MathUtils.random(numberOfPiecesLeft-1);
        //Jumping pieces already in place
        for(int i=0; i<=randomValue; i++){
            if(piecesInPlace[i].getColor().a == 1.0f)
                randomValue++;
        }
        removePiece(sources[randomValue], targets[randomValue]);
    }

    private void removePiece(DragAndDrop.Source source, DragAndDrop.Target target){
        numberOfPiecesLeft--;
        target.getActor().getColor().a = 1.0f;
        removeActor(source.getActor());
        dragAndDrop.removeSource(source);
        dragAndDrop.removeTarget(target);
        target.getActor().getParent().addActorAt(0, target.getActor());

    }

    public Table getPieceSpace(){
        return pieceSpace;
    }

    public Table getPuzzleTable(){
        return puzzleTable;
    }

    public void setCallback(JigsawPuzzleCallback callback){
        this.callback = callback;
    }

    private class JigsawPuzzleDragAndDropSource extends DragAndDrop.Source{

        public JigsawPuzzleDragAndDropSource(Image source){
            super(source);
        }

        @Override
        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Image temp = (Image) this.getActor();
            temp.setVisible(false);
            payload.setObject(new Vector2(event.getStageX(), event.getStageY()));

            int i=0;
            for(; i<scatteredPieces.length; i++){
                if(scatteredPieces[i] == temp)
                    break;
            }
            System.out.println("This piece is number: " +(i+1));

            for(Image placeOfPiece:piecesInPlace){
                if(temp.getDrawable() == placeOfPiece.getDrawable()){
                    placeOfPiece.getParent().addActor(placeOfPiece);
                }
            }

            Image dragImage = new Image(temp.getDrawable());
            payload.setDragActor(dragImage);
            if(temp.getStage() instanceof StandardScreen.StandardStage) {
                StandardScreen.StandardStage stage = (StandardScreen.StandardStage) temp.getStage();
                dragAndDrop.setDragActorPosition(-x - stage.getPadLeft(),
                        -y + dragImage.getHeight() - stage.getPadBottom());
            }
            else {
                dragAndDrop.setDragActorPosition(-x, -y + dragImage.getHeight());
            }

            return payload;
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
            if(target == null){
                getActor().setVisible(true);
                return;
            }

            Image sourceImage = (Image)getActor();


            if(target.getActor() instanceof Table){
                Vector2 cursorOnDragStart = (Vector2)payload.getObject();
                Vector2 stagePosition = new Vector2(    sourceImage.getParent().getX()+sourceImage.getX(),
                                                                sourceImage.getParent().getY()+sourceImage.getY());
                stagePosition.add(event.getStageX()-cursorOnDragStart.x, event.getStageY()-cursorOnDragStart.y);
                sourceImage.remove();
                Table tableTarget = (Table)target.getActor();
                sourceImage.setPosition(    stagePosition.x-tableTarget.getX(),
                                            stagePosition.y-tableTarget.getY());
                tableTarget.addActor(sourceImage);
                sourceImage.setVisible(true);
                return;
            }

            Image targetImage = (Image)target.getActor();

            if(targetImage.getDrawable() == sourceImage.getDrawable()){
                removePiece(this, target);
                if(callback != null)
                    if(numberOfPiecesLeft == 0)
                        callback.onCompleted(targetImage);
                    else
                        callback.onCorrect(targetImage);
            }
            else{
                sourceImage.setVisible(true);
                if(callback != null)
                    callback.onWrong(targetImage);
            }

        }
    }

    private class JigsawPuzzleDragAndDropTarget extends DragAndDrop.Target{

        public JigsawPuzzleDragAndDropTarget(Actor target){
            super(target);
        }

        @Override
        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            return true;
        }

        @Override
        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {

        }

        @Override
        public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
        }
    }

    public interface JigsawPuzzleCallback {
        void onCorrect(Image target);

        void onWrong(Image target);

        void onCompleted(Image lastCorrect);
    }
}

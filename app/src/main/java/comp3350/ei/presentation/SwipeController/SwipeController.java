package comp3350.ei.presentation.SwipeController;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SwipeController extends Callback {
    private boolean swipeBack = false;
    private ButtonsState buttonShowedState = ButtonsState.GONE;
    private RectF buttonInstance = null;
    private RecyclerView.ViewHolder currentItemViewHolder = null;

    private final SwipeButton left;
    private final SwipeButton right;

    private final SwipeControllerActions buttonsActions;
    private static final float buttonWidth = 300;
    private static final float corners = 20;
    private static final float buttonPaddingSide = 10;

    public SwipeController(SwipeControllerActions buttonsActions, SwipeButton left, SwipeButton right) {
        this.buttonsActions = buttonsActions;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                    else if (dX > buttonWidth) buttonShowedState  = ButtonsState.LEFT_VISIBLE;

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                            buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
                        }
                        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                            buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }

                    buttonShowedState = ButtonsState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            buttonInstance = null;
            if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                buttonInstance = left.draw(c, getLeftButtonRect(currentItemViewHolder), corners);
            }
            else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                buttonInstance = right.draw(c, getRightButtonRect(currentItemViewHolder), corners);
            }
        }
    }

    private RectF getLeftButtonRect(RecyclerView.ViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        float buttonWidthWithoutPadding = buttonWidth - buttonPaddingSide;

        return new RectF(itemView.getLeft(), itemView.getTop(),
                itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
    }

    private RectF getRightButtonRect(RecyclerView.ViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        float buttonWidthWithoutPadding = buttonWidth - buttonPaddingSide;

        return new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(),
                itemView.getRight(), itemView.getBottom());
    }
}


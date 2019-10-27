//public class PinnedHeaderExpandableListView extends ExpandableListView {
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_UP:
//                if (mPinnedHeader != null && y >= mPinnedHeader.getTop() && y <= mPinnedHeader.getBottom()) {
//                    int position = pointToPosition(x, y);
//                    int positionGroup = getPackedPositionGroup(getExpandableListPosition(position));
//                    if (positionGroup != INVALID_POSITION) {
//                        if (isGroupExpanded(positionGroup)) {
//                            collapseGroup(positionGroup);
//                        } else {
//                            expandGroup(positionGroup);
//                        }
//                    }
//                    return true;
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//}

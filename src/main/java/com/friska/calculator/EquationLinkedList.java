package com.friska.calculator;

public class EquationLinkedList {

    FloatNode first;
    FloatNode last;

    Node<?, ?> current = null;

    public EquationLinkedList(){

    }

    public EquationLinkedList add(float initFloat){
        this.first = new FloatNode(initFloat);
        this.last = this.first;
        return this;
    }

    public EquationLinkedList add(char operator, float nextFloat){
        if(first == null) throw new SyntaxException("Please initialize first float value.");
        final FloatNode f = new FloatNode(nextFloat);
        final OperatorNode o = new OperatorNode(operator, last, f);
        f.setPrev(o);
        last.setNext(o);
        last = f;
        return this;
    }

    public Node<?, ?> getNext(){
        if(current == null) current = first;
        else current = current.next;
        return current;
    }

    public void resetNext(){
        current = null;
    }

    public Node<?, ?> getCurrent() {
        return current;
    }

    public boolean hasNext(){
        if(current == null) return first != null;
        return current.next != null;
    }

    public void debugSout(){
        resetNext();
        StringBuilder sb = new StringBuilder();
        while(hasNext()) {
            sb.append(getNext());
        }
        System.out.println(sb);
    }

    public float calculate(){
        evaluate('^');
        evaluate('*', '/');
        evaluate('+', '-');
        return first.getData();
    }

    private void evaluate(char... operators){
        resetNext();
        while(hasNext()){
            Node<?,?> c = getNext();
            if(c instanceof OperatorNode && compareOperators(operators, ((OperatorNode) c).getData())){
                simplify();
            }
        }
    }

    private boolean compareOperators(char[] operators, char operator){
        for (char c : operators) {
            if(c == operator) return true;
        }
        return false;
    }

    public void simplify(){
        if(current instanceof FloatNode) return;
        OperatorNode curr = (OperatorNode) current;
        float result = evaluate(curr.getPrev().getData(),
                                curr.getNext().getData(),
                                curr.getData());

        FloatNode res = new FloatNode(result);
        OperatorNode op1 = curr.getPrev().getPrev();
        OperatorNode op2 = curr.getNext().getNext();
        curr.getPrev().setPrev(null);
        curr.getNext().setNext(null);
        if(op1 != null){
            op1.setNext(res);
        }else{
            first = res;
        }
        if(op2 != null){
            op2.setPrev(res);
        }

        res.setPrev(op1);
        res.setNext(op2);
        current = res;
    }

    private float evaluate(float num1, float num2, char operator){
        switch (operator){
            case '+' -> {return num1 + num2;}
            case '-' -> {return num1 - num2;}
            case '*' -> {return num1 * num2;}
            case '/' -> {return num1 / num2;}
            case '^' -> {return (float) Math.pow(num1, num2);}
            default -> throw new SyntaxException("Unknown operator " + operator);
        }
    }

    //--------------------------Node Classes----------------------------
    private static class Node<D, T extends Node<?, ?>>{
        protected T next;
        protected T prev;
        protected final D data;
        protected Node(D data){this.data = data;}
        public void setNext(T next) {this.next = next;}
        public void setPrev(T prev) {this.prev = prev;}
        public Node<?, ?> getNext() {return next;}
        public Node<?, ?> getPrev() {return prev;}

        public D getData(){
            return data;
        }

        @Override
        public String toString() {
            return getData().toString();
        }
    }

    private static class FloatNode extends Node<Float, OperatorNode>{

        private FloatNode(float data){this(data, null);}
        private FloatNode(float data, OperatorNode prev){
            super(data);
            next = null;
            this.prev = prev;
        }

        @Override
        public OperatorNode getNext() {
            return next;
        }

        @Override
        public OperatorNode getPrev() {
            return prev;
        }
    }

    private static class OperatorNode extends Node<Character, FloatNode>{
        public OperatorNode(Character data, FloatNode prev, FloatNode next){
            super(data);
            assert prev != null;
            assert next != null;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public FloatNode getNext() {
            return next;
        }

        @Override
        public FloatNode getPrev() {
            return prev;
        }
    }

}

package jdk.concurrent.print_fish;

/**
 * 有三种线程
 * Ta若干: 死循环打印 <
 * Tb若干: 死循环打印 >
 * Tc若干: 死循环打印 _
 * 任务：
 * 对线程同步，使得屏幕打印出 <><_ 和 ><>_ 的组合
 */

import java.util.*;

public class fish {

    enum State {A, B, C, D, E, F}

    static class Rule {
        final State from;
        final char ch;
        final State to;

        Rule(State from, char ch, State to) {
            this.from = from;
            this.ch = ch;
            this.to = to;
        }
    }

    static final Rule[] rules = {
            new Rule(State.A, '<', State.B),
            new Rule(State.B, '>', State.C),
            new Rule(State.C, '<', State.D),
            new Rule(State.A, '>', State.E),
            new Rule(State.E, '<', State.F),
            new Rule(State.F, '>', State.D),
            new Rule(State.D, '_', State.A),
    };

    static class Monitor {
        private State state = State.A;
        private final Random rand = new Random();
        private final Object lock = new Object();

        // 查找当前状态下能打印的规则
        private Rule getRule(char ch) {
            List<Rule> candidates = new ArrayList<>();
            for (Rule rule : rules) {
                if (rule.from == state && rule.ch == ch) {
                    candidates.add(rule);
                }
            }
            if (candidates.isEmpty()) return null;
            // 如果有多个候选（比如A可以出<或>），随机选一个
            return candidates.get(rand.nextInt(candidates.size()));
        }

        public void printChar(char ch) throws InterruptedException {
            synchronized (lock) {
                Rule rule;
                while ((rule = getRule(ch)) == null) {
                    lock.wait();
                }
                if (ch == '_') {
                    System.out.println("_");
                } else {
                    System.out.print(ch);
                }
                state = rule.to;
                lock.notifyAll();
            }
        }
    }

    static class Ta extends Thread {
        private final Monitor monitor;

        public Ta(Monitor monitor) {
            this.monitor = monitor;
        }

        public void run() {
            while (true) {
                try {
                    monitor.printChar('<');
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    static class Tb extends Thread {
        private final Monitor monitor;

        public Tb(Monitor monitor) {
            this.monitor = monitor;
        }

        public void run() {
            while (true) {
                try {
                    monitor.printChar('>');
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    static class Tc extends Thread {
        private final Monitor monitor;

        public Tc(Monitor monitor) {
            this.monitor = monitor;
        }

        public void run() {
            while (true) {
                try {
                    monitor.printChar('_');
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        new Ta(monitor).start();
        new Ta(monitor).start();
        new Tb(monitor).start();
        new Tb(monitor).start();
        new Tc(monitor).start();
        new Tc(monitor).start();
    }
}


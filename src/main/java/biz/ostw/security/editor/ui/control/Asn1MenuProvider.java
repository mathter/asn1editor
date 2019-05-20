package biz.ostw.security.editor.ui.control;

import javafx.scene.control.MenuItem;
import org.bouncycastle.asn1.ASN1Primitive;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Asn1MenuProvider {

    private static SoftReference<List<Asn1MenuProvider>> REF = new SoftReference<>(null);

    private static ReadWriteLock RWL = new ReentrantReadWriteLock();

    public static final Collection<Asn1MenuProvider> providers() {

        RWL.readLock().lock();
        try {
            if (REF.get() == null) {
                RWL.readLock().unlock();
                RWL.writeLock().lock();
                try {
                    final List<Asn1MenuProvider> c = new ArrayList<>();

                    for (Asn1MenuProvider provider : ServiceLoader.load(Asn1MenuProvider.class)) {
                        c.add(provider);
                    }
                    REF = new SoftReference<>(c);
                } finally {
                    RWL.readLock().lock();
                    RWL.writeLock().unlock();
                }
            }

            return REF.get();
        } finally {
            RWL.readLock().unlock();
        }
    }

    public static final List<MenuItem> provide(final ASN1Primitive asn1) {

        return Asn1MenuProvider.providers().stream().collect(
                ArrayList::new,
                (items, provider) -> items.addAll(provider.get(asn1)),
                (left, right) -> left.addAll(right)
        );
    }

    public abstract List<MenuItem> get(ASN1Primitive asn1);

    public abstract Object key();
}

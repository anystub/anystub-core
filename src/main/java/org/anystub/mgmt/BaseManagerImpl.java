package org.anystub.mgmt;

import org.anystub.AnyStubFileLocator;
import org.anystub.AnyStubId;
import org.anystub.Base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BaseManagerImpl implements BaseManager {
    private static BaseManagerImpl baseManager = new BaseManagerImpl();
    private List<Base> list = new ArrayList<>();
    public static final String DEFAULT_STUB_PATH = new File("src/test/resources/anystub/stub.yml").getPath();
    public static final String DEFAULT_PATH = new File("src/test/resources/anystub").getPath();

    public static BaseManagerImpl instance() {
        return baseManager;
    }

    protected BaseManagerImpl() {

    }

    /**
     * returns default stub
     *
     * @return
     */
    public Base getBase() {
        return getBase(DEFAULT_STUB_PATH);
    }

    /**
     * returns stub with specific path
     *
     * @param filename
     * @return
     */
    public Base getBase(String filename) {

        String fullPath = filename == null || filename.isEmpty() ?
                DEFAULT_STUB_PATH :
                getFilePath(filename);

        return get(fullPath).orElseGet(new Supplier<Base>() {
            @Override
            public Base get() {
                Base base = new Base(fullPath);
                stubInitialization(base);
                list.add(base);
                return base;
            }
        });
    }

    private Optional<Base> get(String fullFileName) {
        return list
                .stream()
                .filter(base -> base.getFilePath().equals(fullFileName))
                .findFirst();
    }

    /**
     * returns path for default stub
     *
     * @return
     */
    public static String getFilePath(String filename) {
        File file = new File(filename);
        if (file.getParentFile() == null || file.getParent().isEmpty()) {
            return DEFAULT_PATH + File.separator + file.getPath();
        }
        return file.getPath();
    }


    /**
     * returns stub for current test
     *
     * @return stub for current test
     */
    public Base getStub() {
        AnyStubId s = AnyStubFileLocator.discoverFile();
        if (s != null) {
            return getBase(s.filename())
                    .constrain(s.requestMode());
        }

        return getBase();
    }

    @Override
    public Base getStub(String suffix) {
        AnyStubId s = AnyStubFileLocator.discoverFile(suffix);
        if (s != null) {
            return getBase(s.filename())
                    .constrain(s.requestMode());
        }

        return getBase();
    }


    protected void stubInitialization(Base newStub) {
        // default initializer does nothing
    }

}

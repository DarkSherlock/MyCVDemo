package com.liang.tind.mycv;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Sherlock on 2017/8/16.
 */

public class SherlockTinkerApplication extends TinkerApplication {
    public SherlockTinkerApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.liang.tind.mycv.SherlockApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}

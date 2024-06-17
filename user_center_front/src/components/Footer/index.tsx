import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'danta',
          title: 'danta.live',
          href: 'https://danta.live',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined /> github</>,
          href: 'https://github.com/abc123-eng',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
